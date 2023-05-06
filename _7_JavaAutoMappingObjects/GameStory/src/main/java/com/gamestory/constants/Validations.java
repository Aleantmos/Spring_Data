package com.gamestory.constants;


import java.math.BigDecimal;

public enum Validations {
    ;
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$";

    public static final String EMAIL_NOT_FOUND_MESSAGE = "Incorrect email.";
    public static final String PASSWORD_OR_USERNAME_NOT_FOUND_MESSAGE = "Incorrect username or password.";
    public static final String CONFIRM_PASSWORD_DIFFERENT_MESSAGE = "Passwords are not matching.";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found.";
    public static final String SUCCESSFUL_REGISTER_MESSAGE = " was registered.";
    public static final String EMAIL_EXISTS_MESSAGE = "Email already exists.";
    public static final String LOGIN_SUCCESSFUL_MESSAGE = "Successfully logged in ";

    public final static String LOGOUT_ERROR_MESSAGE = "Cannot log out. No user was logged in.";
    public final static String SUCCESSFUL_LOGOUT_MESSAGE = "User %s successfully logged out.";
    public final static String INVALID_GAME_TITLE_MESSAGE = "Not a valid game title.";
    public final static String INVALID_PRICE_OR_SIZE_MESSAGE = "Price or size should be positive number.";
    public final static String INVALID_TRAILER_ID_MESSAGE = "Trailer ID should be exactly 11 characters.";
    public final static String INVALID_LINK_MESSAGE = "Link should begin with http(s)...";
    public final static String INVALID_DESCRIPTION_MESSAGE = "Description should be at least 20 characters.";
    public final static String ADDED_GAME_MESSAGE = "Added %s successfully.";
    public final static String IMPOSSIBLE_COMMAND = "No admin rights";



}
