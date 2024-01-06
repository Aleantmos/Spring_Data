package com.jsonex;

import com.jsonex.services.CategoryService;
import com.jsonex.services.ProductService;
import com.jsonex.services.SeedService;
import com.jsonex.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public CommandRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.seedService.seedAll();

        productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1000));

        this.userService.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName();
        this.categoryService.getCategorySummary();
    }
}
