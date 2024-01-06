package com.jsonex.services;

import com.jsonex.domain.dto.products.ProductDto;
import com.jsonex.domain.dto.products.ProductInRangeWithoutBuyerDto;
import com.jsonex.repostitories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.jsonex.constants.Paths.PRODUCTS_WITHOUT_BUYERS_IN_RANGE_JSON_PATH;
import static com.jsonex.constants.Utils.MODEL_MAPPER;
import static com.jsonex.constants.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithoutBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException {
        List<ProductInRangeWithoutBuyerDto> products = productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductInRangeWithoutBuyerDto)
                .collect(Collectors.toList());

        writeJsonIntoFile(products, PRODUCTS_WITHOUT_BUYERS_IN_RANGE_JSON_PATH);

        return products;
    }
}
