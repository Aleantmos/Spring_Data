package com.example.workshop.services;

import com.example.workshop.model.users.LoginDTO;
import com.example.workshop.model.users.User;
import com.example.workshop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(LoginDTO loginDTO) {
        Optional<User> optionalUser = this.userRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        return optionalUser;
    }
}
