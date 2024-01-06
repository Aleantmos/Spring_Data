package com.jsonex.domain.dto.categories;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsSummaryDto {
    @SerializedName("name")

    private String category;
    private Long productsCount;
    private Double averagePrice;
    private BigDecimal totalRevenue;
}
