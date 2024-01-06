package com.jsonex.services;

import com.jsonex.domain.dto.categories.CategoryProductsSummaryDto;
import com.jsonex.repostitories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.jsonex.constants.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static com.jsonex.constants.Utils.writeJsonIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductsSummaryDto> getCategorySummary() throws IOException {
        final List<CategoryProductsSummaryDto> categoryProductsSummaries = this.categoryRepository.getCategorySummary()
                .orElseThrow(NoSuchElementException::new);

        writeJsonIntoFile(categoryProductsSummaries, CATEGORIES_BY_PRODUCTS_JSON_PATH);

        return categoryProductsSummaries;
    }
}
