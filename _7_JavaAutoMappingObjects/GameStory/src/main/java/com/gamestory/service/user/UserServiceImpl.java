package com.gamestory.service.user;

import com.gamestory.domain.dto.UserLoginDTO;
import com.gamestory.domain.dto.UserRegisterDTO;
import com.gamestory.domain.entity.User;
import com.gamestory.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gamestory.constants.Validations.*;

@Service
public class UserServiceImpl implements UserService {

    private User loggedUser;
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

        boolean isUserFound = userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent();

        if (isUserFound) {
            throw new IllegalArgumentException(EMAIL_EXISTS_MESSAGE);
        }

        this.userRepository.save(user);

        return userRegisterDTO.successfulRegisterFormat();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        final UserLoginDTO userLogin = new UserLoginDTO(email, password);

        Optional<User> user = userRepository.findByEmail(userLogin.getEmail());

        if (user.isPresent() && this.loggedUser == null && user.get().getPassword().equals(userLogin.getPassword())) {
            this.loggedUser = user.get();
            return LOGIN_SUCCESSFUL_MESSAGE + this.loggedUser.getFullName() ;
        }

        return PASSWORD_OR_USERNAME_NOT_FOUND_MESSAGE;
    }

    @Override
    public String logout() {

        if (this.loggedUser == null) {
            return LOGOUT_ERROR_MESSAGE;
        }

        String output = String.format(SUCCESSFUL_LOGOUT_MESSAGE, loggedUser.getFullName());

        this.loggedUser = null;

        return output;
    }

    @Override
    public User getLoggedUser() {
        return this.loggedUser;
    }


}
