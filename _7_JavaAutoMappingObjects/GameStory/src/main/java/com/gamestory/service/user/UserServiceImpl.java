package com.gamestory.service.user;

import com.gamestory.domain.dto.UserRegisterDTO;
import com.gamestory.domain.entity.User;
import com.gamestory.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(email, password, confirmPassword, fullName);

        User user = this.modelMapper.map(userRegisterDTO, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.save(user);

        return userRegisterDTO.successfulRegisterFormat();
    }
}
