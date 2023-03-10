package softuni.bookshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bookshop.model.entity.Category;
import softuni.bookshop.repository.CategoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final String CATEGORY_FILE_PATH = "src/main/resources/files/categories.txt";

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategories() throws IOException {

        if (categoryRepository.count() == 0) {
            Files.readAllLines(Path.of(CATEGORY_FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(categoryName -> {
                        Category category = new Category();
                        category.setName(categoryName);

                        categoryRepository.save(category);
                    });
        }
    }

    public Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();

        int randomInt = ThreadLocalRandom.current().nextInt(1, 3);

        for (int i = 0; i < randomInt; i++) {

            long categoryRepoCount = categoryRepository.count();

            long randomId = ThreadLocalRandom
                    .current()
                    .nextLong(1, categoryRepoCount + 1);


            Category category = categoryRepository.findById(randomId).orElse(null);

            categories.add(category);
        }

        return categories;
    }
}
