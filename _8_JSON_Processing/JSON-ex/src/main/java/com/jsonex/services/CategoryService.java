package com.jsonex.services;

import com.jsonex.domain.dto.categories.CategoryProductsSummaryDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryProductsSummaryDto> getCategorySummary() throws IOException;
}
