package com.jsonex.domain.dto.products;

import com.jsonex.domain.dto.products.ProductBasicInfo;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSoldWithCountDto {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductBasicInfo> products;

    public ProductsSoldWithCountDto(List<ProductBasicInfo> products) {
        this.products = products;
        this.count = products.size();
    }
}
