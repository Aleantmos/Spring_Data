package com.jsonex.domain.dto.users;

import com.jsonex.domain.dto.products.ProductBasicInfo;
import com.jsonex.domain.dto.products.ProductDto;
import com.jsonex.domain.entities.Product;
import com.jsonex.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<ProductDto> sellingProducts;
    private Set<ProductDto> boughtProducts;
    private Set<UserDto> friends;

    public String getFullName() {
        return firstName + " " + lastName;
    }

//    public UsersWithProductsWrapperDto toUserWithProductsWrapperDto() {
//        return new UsersWithProductsWrapperDto();
//    }

    public UserWithProductsDto toUserWithProductDto() {
        return new UserWithProductsDto(firstName, lastName, age, toProductsSoldWithCountDto());
    }

    public ProductsSoldWithCountDto toProductsSoldWithCountDto() {
        return new ProductsSoldWithCountDto(sellingProducts.stream()
                .map(this::toProductBasicInfo)
                .collect(Collectors.toList()));
    }

    public ProductBasicInfo toProductBasicInfo(ProductDto productDto) {
        return new ProductBasicInfo(productDto.getName(), productDto.getPrice());
    }
}


