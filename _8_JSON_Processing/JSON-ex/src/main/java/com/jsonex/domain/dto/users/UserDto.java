package com.jsonex.domain.dto.users;

import com.jsonex.domain.entities.Product;
import com.jsonex.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<Product> sellingProducts;
    private Set<Product> boughtProducts;
    private Set<User> friends;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
