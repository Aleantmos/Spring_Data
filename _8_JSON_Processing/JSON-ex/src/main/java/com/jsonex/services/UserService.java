package com.jsonex.services;

import com.jsonex.domain.dto.users.UsersWithSoldProductsDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException;
}
