package com.jsonex.services;

import com.jsonex.domain.dto.categories.CategoryImportDto;
import com.jsonex.domain.dto.products.ProductImportDto;
import com.jsonex.domain.dto.users.UserImportDto;
import com.jsonex.domain.entities.Category;
import com.jsonex.domain.entities.Product;
import com.jsonex.domain.entities.User;
import com.jsonex.repostitories.CategoryRepository;
import com.jsonex.repostitories.ProductRepository;
import com.jsonex.repostitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());
            final List<User> users = Arrays.stream(GSON.fromJson(fileReader, UserImportDto[].class))
                    .map(user -> MODEL_MAPPER.map(user, User.class))
                    .collect(Collectors.toList());

            this.userRepository.saveAllAndFlush(users);
            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORY_JSON_PATH.toFile());

            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDto[].class))
                    .map(category -> MODEL_MAPPER.map(category, Category.class))
                    .collect(Collectors.toList());

            categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH.toFile());

            Arrays.stream(GSON.fromJson(fileReader, ProductImportDto[].class))
                            .map(product -> MODEL_MAPPER.map(product, Product.class))
                                    .map(this::setRandomSeller)
                                    .map(this::setRandomBuyer)
                                    .map(this::setRandomCategories);
            fileReader.close();
        }
    }

    private Product setRandomCategories(Product product) {
        return null;
    }

    private Product setRandomBuyer(Product product) {
        User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        while (buyer.equals(product.getSeller())) {
            buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        }

        product.setBuyer(buyer);

        return product;
    }

    private Product setRandomSeller(Product product) {
        User seller  = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        product.setSeller(seller);

        return product;
    }
}
