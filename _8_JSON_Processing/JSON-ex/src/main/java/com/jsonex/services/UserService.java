package com.jsonex.services;

import com.jsonex.domain.dto.users.wrappers.UsersWithProductsWrapperDto;
import com.jsonex.domain.dto.users.UserWithSoldProductsDto;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException, JAXBException;

    UsersWithProductsWrapperDto usersAndProducts()
            throws IOException, JAXBException;
}
