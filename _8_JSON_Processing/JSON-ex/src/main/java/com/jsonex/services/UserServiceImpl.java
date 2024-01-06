package com.jsonex.services;

import com.jsonex.domain.dto.users.UsersWithSoldProductsDto;
import com.jsonex.repostitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.jsonex.constants.Paths.USERS_WITH_SOLD_PRODUCTS_JSON_PATH;
import static com.jsonex.constants.Utils.MODEL_MAPPER;
import static com.jsonex.constants.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UsersWithSoldProductsDto> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
            throws IOException {
        final List<UsersWithSoldProductsDto> usersWithSoldProducts = this.userRepository
                .findAllBySellingProductsBuyerIsNotNull()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

        writeJsonIntoFile(usersWithSoldProducts, USERS_WITH_SOLD_PRODUCTS_JSON_PATH);

        return usersWithSoldProducts;

    }
}
