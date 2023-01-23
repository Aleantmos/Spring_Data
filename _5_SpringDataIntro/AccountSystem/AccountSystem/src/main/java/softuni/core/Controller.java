package softuni.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.entities.Account;
import softuni.entities.Client;
import softuni.service.account.AccountService;
import softuni.service.user.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static softuni.constants.ExceptionMessages.NEGATIVE_AMOUNT;
import static softuni.constants.Messages.*;




@Component
public class Controller implements CommandLineRunner {

    private final Scanner scan = new Scanner(System.in);

    private UserService userService;
    private AccountService accountService;

    private String command = "";
    private String result = "";
    private long id = -1;
    private BigDecimal amount;
    private List<String> tokens = new ArrayList<>();

    @Autowired
    public Controller(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }


    @Override
    public void run(String... args) throws Exception {


        System.out.println("Choose action:");
        while (!"End".equals(command = scan.nextLine().trim())) {
            try {
                switch (command) {
                    case "Register":

                        System.out.println("Please choose a username:");
                        tokens.add(scan.nextLine());

                        System.out.println("Please give your age:");
                        tokens.add(scan.nextLine());


                        String name = tokens.get(0);
                        int age = Integer.parseInt(tokens.get(1));
                        Client newClient =
                                Client.builder()
                                        .username(name)
                                        .age(age)
                                        .build();

                        result = userService.registerUser(newClient);

                        Account account = Account.builder()
                                .balance(new BigDecimal(0))
                                .client(newClient)
                                .build();

                        accountService.registerUserAccount(account);
                        System.out.println(result);
                        break;

                    case "Deposit":
                        id = getUserId(scan);

                        System.out.println("Please enter amount to deposit:");
                        amount = validateAmount(scan);

                        accountService.depositAmount(id, amount);
                        System.out.println(AMOUNT_SUCCESSFULLY_ADDED);
                        break;

                    case "Withdraw":

                        id = getUserId(scan);

                        System.out.println("Please enter withdrawal amount:");
                        amount = validateAmount(scan);

                        accountService.withdrawAmount(id, amount);
                        System.out.println(AMOUNT_SUCCESSFULLY_WITHDRAWN);
                        break;

                    case "Transfer":
                        getTransactionInfo(scan);
                        System.out.println(AMOUNT_SUCCESSFULLY_TRANSFERRED);
                        break;
                    default:
                        System.out.println(NO_SUCH_TRANSACTION);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }


    }

    private void getTransactionInfo(Scanner scan) {

        long transferFrom = getUserId(scan);

        long transferTo = getUserId(scan);

        System.out.println("Enter transfer amount:");
        BigDecimal amount = validateAmount(scan);

        accountService.transferAmount(transferFrom, transferTo, amount);

    }

    private BigDecimal validateAmount(Scanner scan){

        BigDecimal amount = new BigDecimal(scan.nextLine().trim());

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(NEGATIVE_AMOUNT);
        }

        return amount;
    }

    private long getUserId(Scanner scan) {
        long id;
        System.out.println("Please enter id:");

        id = Long.parseLong(scan.nextLine().trim());

        while (userService.findUserById(id) == null) {
            System.out.println(WRONG_ID);
            id = Long.parseLong(scan.nextLine());
        }
        return id;
    }
}
