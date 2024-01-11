package com.jsonex.domain.dto.products.wrappers;

import com.jsonex.domain.dto.products.SingleProductInRangeWithoutBuyerDto;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeWithNoBuyerWrapperDto {

    @XmlElement(name = "product")
    private List<SingleProductInRangeWithoutBuyerDto> products;

}
