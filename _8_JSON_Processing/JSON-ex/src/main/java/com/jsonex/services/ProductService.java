package com.jsonex.services;

import com.jsonex.domain.dto.products.ProductInRangeWithoutBuyerDto;
import com.jsonex.domain.entities.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductInRangeWithoutBuyerDto>
            findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException;

}
