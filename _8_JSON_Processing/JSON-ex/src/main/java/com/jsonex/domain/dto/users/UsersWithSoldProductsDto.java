package com.jsonex.domain.dto.users;

import com.jsonex.domain.dto.products.ProductSoldDto;
import com.jsonex.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDto {
    private String firstName;
    private String lastName;
    private List<ProductSoldDto> boughtProducts;
}
