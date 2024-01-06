package com.jsonex.domain.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithProductsDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private ProductsSoldWithCountDto products;
}
