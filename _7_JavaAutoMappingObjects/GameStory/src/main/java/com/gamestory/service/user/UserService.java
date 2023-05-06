package com.gamestory.service.user;

import com.gamestory.domain.entity.User;

public interface UserService {
    String registerUser (String[] args);
    String loginUser (String[] args);
    String logout();
    User getLoggedUser();
}
