package softuni.bookshop.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.bookshop.service.CategoryService;
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.addCategories();
    }
}
