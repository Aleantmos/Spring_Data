package com.jsonex.domain.dto.categories;

import com.google.gson.annotations.SerializedName;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryProductsSummaryDto {
    @SerializedName("name")
    @XmlAttribute(name = "name")
    private String category;
    @XmlElement(name = "products-count")
    private Long productsCount;
    @XmlElement(name = "average-price")
    private Double averagePrice;
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;
}
