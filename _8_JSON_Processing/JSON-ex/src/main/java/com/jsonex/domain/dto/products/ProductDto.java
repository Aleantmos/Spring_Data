package com.jsonex.domain.dto.products;

import com.jsonex.domain.dto.categories.CategoryDto;
import com.jsonex.domain.dto.users.UserDto;
import com.jsonex.domain.entities.Category;
import com.jsonex.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private BigDecimal price;
    private UserDto buyer;
    private UserDto seller;
    private Set<CategoryDto> categories;

    public ProductInRangeWithoutBuyerDto toProductInRangeWithoutBuyerDto() {
        return new ProductInRangeWithoutBuyerDto(name, price, seller.getFullName());
    }
}
