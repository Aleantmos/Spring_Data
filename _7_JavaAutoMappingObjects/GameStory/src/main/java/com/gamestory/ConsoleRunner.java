package com.gamestory;

import com.gamestory.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.gamestory.constants.Commands.LOGIN_USER;
import static com.gamestory.constants.Commands.REGISTER_USER;
import static com.gamestory.constants.Validations.COMMAND_NOT_FOUND_MESSAGE;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String output = "";
        while (!scanner.nextLine().equals("close")) {
            final String[] input = scanner.nextLine().split("\\|");
            final String command = input[0];

            output = switch (command) {
                case REGISTER_USER -> userService.registerUser(input);
                //case LOGIN_USER -> userService.loginUser(input);
                default -> COMMAND_NOT_FOUND_MESSAGE;
            };
        }


        System.out.println(output);
    }
}
