package com.jsonex.services;

import com.jsonex.domain.dto.products.ProductDto;
import com.jsonex.domain.dto.products.SingleProductInRangeWithoutBuyerDto;
import com.jsonex.domain.dto.products.wrappers.ProductsInRangeWithNoBuyerWrapperDto;
import com.jsonex.repostitories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.jsonex.constants.Paths.PRODUCTS_WITHOUT_BUYERS_IN_RANGE_JSON_PATH;
import static com.jsonex.constants.Paths.PRODUCTS_WITHOUT_BUYERS_IN_RANGE_XML_PATH;
import static com.jsonex.constants.Utils.*;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<SingleProductInRangeWithoutBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException, JAXBException {
        List<SingleProductInRangeWithoutBuyerDto> products =
                productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductInRangeWithoutBuyerDto)
                .collect(Collectors.toList());

        final ProductsInRangeWithNoBuyerWrapperDto productsWrapper = new ProductsInRangeWithNoBuyerWrapperDto(products);

        writeJsonIntoFile(products, PRODUCTS_WITHOUT_BUYERS_IN_RANGE_JSON_PATH);

        writeXMLIntoFile(productsWrapper, PRODUCTS_WITHOUT_BUYERS_IN_RANGE_XML_PATH);

        return products;
    }
}
