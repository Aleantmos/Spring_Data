package com.gamestory.constants;

public enum Validations {
    ;
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$";

    public static final String EMAIL_NOT_FOUND_MESSAGE = "Incorrect email.";
    public static final String PASSWORD_OR_USERNAME_NOT_FOUND_MESSAGE = "Incorrect username or password.";
    public static final String CONFIRM_PASSWORD_DIFFERENT_MESSAGE = "Passwords are not matching.";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found.";
    public static final String SUCCESSFUL_REGISTER_MESSAGE = " was registered.";

}
