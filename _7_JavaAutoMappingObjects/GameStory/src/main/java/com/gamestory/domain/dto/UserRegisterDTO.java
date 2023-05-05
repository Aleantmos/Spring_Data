package com.gamestory.domain.dto;

import com.gamestory.constants.Validations;

import java.util.regex.Pattern;

import static com.gamestory.constants.Validations.*;

public class UserRegisterDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void validate() throws IllegalArgumentException {
        final boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, this.email);

        if (!isEmailValid) {
            throw new IllegalArgumentException(EMAIL_NOT_FOUND_MESSAGE);
        }

        final boolean isPasswordValid = Pattern.matches(PASSWORD_PATTERN, this.password);

        if (!isPasswordValid) {
            throw new IllegalArgumentException(PASSWORD_OR_USERNAME_NOT_FOUND_MESSAGE);

        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(CONFIRM_PASSWORD_DIFFERENT_MESSAGE);

        }

    }

    public String successfulRegisterFormat() {
        return fullName + SUCCESSFUL_REGISTER_MESSAGE;
    }
}
