package com.jsonex.domain.dto.users;

import com.jsonex.domain.dto.products.ProductSoldDto;
import com.jsonex.domain.dto.products.wrappers.ProductsSoldWrapperDto;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithSoldProductsDto {
    private String firstName;
    private String lastName;
    private List<ProductSoldDto> boughtProducts;

    public static List<UserWithSoldProductsXmlDto> toUsersWithSoldProductsDto(List<UserWithSoldProductsDto> input) {
        return input.stream()
                .map(user -> new UserWithSoldProductsXmlDto(
                        user.getFirstName(),
                        user.getLastName(),
                        new ProductsSoldWrapperDto(user.getBoughtProducts())))
                .collect(Collectors.toList());
    }
}
