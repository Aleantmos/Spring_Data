package com.jsonex.services;

import com.jsonex.domain.dto.products.SingleProductInRangeWithoutBuyerDto;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<SingleProductInRangeWithoutBuyerDto>
            findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException, JAXBException;

}
