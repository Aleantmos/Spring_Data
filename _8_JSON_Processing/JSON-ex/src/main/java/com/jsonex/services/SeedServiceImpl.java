package com.jsonex.services;

import com.jsonex.domain.dto.categories.wrappers.CategoriesImportWrapperDto;
import com.jsonex.domain.dto.products.ProductImportDto;
import com.jsonex.domain.dto.products.wrappers.ProductsImportWrapperDto;
import com.jsonex.domain.dto.users.wrappers.UsersImportWrapperDto;
import com.jsonex.domain.entities.Category;
import com.jsonex.domain.entities.Product;
import com.jsonex.domain.entities.User;
import com.jsonex.repostitories.CategoryRepository;
import com.jsonex.repostitories.ProductRepository;
import com.jsonex.repostitories.UserRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jsonex.constants.Paths.*;
import static com.jsonex.constants.Utils.GSON;
import static com.jsonex.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(USER_XML_PATH.toFile());

//                        final List<User> users = Arrays.stream(GSON.fromJson(fileReader, UserImportDto[].class))
//                    .map(user -> MODEL_MAPPER.map(user, User.class))
//                    .collect(Collectors.toList());
//
//            this.userRepository.saveAllAndFlush(users);


            final JAXBContext context = JAXBContext.newInstance(UsersImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            UsersImportWrapperDto usersWrapperDto = (UsersImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<User> users = usersWrapperDto.getUsers()
                    .stream()
                    .map(userDto -> MODEL_MAPPER.map(userDto, User.class))
                    .toList();
            this.userRepository.saveAllAndFlush(users);
            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORY_XML_PATH.toFile());

//            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDto[].class))
//                    .map(category -> MODEL_MAPPER.map(category, Category.class))
//                    .collect(Collectors.toList());
//

            final JAXBContext context = JAXBContext.newInstance(CategoriesImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();


            final CategoriesImportWrapperDto categoriesWrapperDto = (CategoriesImportWrapperDto) unmarshaller
                    .unmarshal(fileReader);

            List<Category> categories = categoriesWrapperDto.getCategories()
                    .stream()
                    .map(c -> MODEL_MAPPER.map(c, Category.class))
                    .collect(Collectors.toList());

            categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(PRODUCTS_XML_PATH.toFile());

//            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDto[].class))
//                    .map(product -> MODEL_MAPPER.map(product, Product.class))
//                    .map(this::setRandomSeller)
//                    .map(this::setRandomBuyer)
//                    .map(this::setRandomCategories)
//                    .collect(Collectors.toList());

            JAXBContext context = JAXBContext.newInstance(ProductsImportWrapperDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ProductsImportWrapperDto productsWrapperDto = (ProductsImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<Product> products = productsWrapperDto.getProducts().stream()
                    .map(p -> MODEL_MAPPER.map(p, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .collect(Collectors.toList());

            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomCategories(Product product) {
        final Random random = new Random();

        long high = this.categoryRepository.count();

        int numberOfCategories = random.nextInt(0, (int) high);

        Set<Category> categories = new HashSet<>();

        IntStream.range(0, numberOfCategories)
                .forEach(num -> {
                    Category category = this.categoryRepository
                            .getRandomEntity()
                            .orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });

        product.setCategories(categories);

        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {

            User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
            while (buyer.equals(product.getSeller())) {
                buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
            }

            product.setBuyer(buyer);
        }

        return product;
    }

    private Product setRandomSeller(Product product) {
        User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        product.setSeller(seller);

        return product;
    }
}
