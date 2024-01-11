package com.jsonex.services;

import com.jsonex.domain.dto.categories.CategoryProductsSummaryDto;
import com.jsonex.domain.dto.categories.wrappers.CategoriesProductSummaryWrapperDto;
import com.jsonex.repostitories.CategoryRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.jsonex.constants.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static com.jsonex.constants.Paths.CATEGORIES_BY_PRODUCTS_XML_PATH;
import static com.jsonex.constants.Utils.writeJsonIntoFile;
import static com.jsonex.constants.Utils.writeXMLIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductsSummaryDto> getCategorySummary() throws IOException, JAXBException {
        final List<CategoryProductsSummaryDto> categoryProductsSummaries = this.categoryRepository.getCategorySummary()
                .orElseThrow(NoSuchElementException::new);

        final CategoriesProductSummaryWrapperDto categoriesWrapper =
                new CategoriesProductSummaryWrapperDto(categoryProductsSummaries);

        writeJsonIntoFile(categoryProductsSummaries, CATEGORIES_BY_PRODUCTS_JSON_PATH);
        writeXMLIntoFile(categoriesWrapper, CATEGORIES_BY_PRODUCTS_XML_PATH);

        return categoryProductsSummaries;
    }
}
