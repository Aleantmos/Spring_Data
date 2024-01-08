package bg.softuni.modelmapper;

import bg.softuni.modelmapper.entities.dto.AddressDto;
import bg.softuni.modelmapper.entities.dto.CreateEmployeeDto;
import bg.softuni.modelmapper.services.AddressService;
import bg.softuni.modelmapper.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;

    public AppMain(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);

//       createAddress(scan);
//       createEmployee(scan);

        printAllEmployees();
    }

    private void printAllEmployees() {
        this.employeeService.findAll()
                .forEach(System.out::println);
    }

    private void createEmployee(Scanner scan) {
        String firstName = scan.nextLine();
        BigDecimal salary = new BigDecimal(scan.nextLine());
        LocalDate birthday = LocalDate.parse(scan.nextLine());

//        long addressId = Long.parseLong(scan.nextLine());

        String country = scan.nextLine();
        String city = scan.nextLine();

        AddressDto addressDto = new AddressDto(country, city);

        CreateEmployeeDto employeeDto = new CreateEmployeeDto(firstName, null,salary, birthday, addressDto);

        this.employeeService.create(employeeDto);

    }

    private void createAddress(Scanner scan) {
        String country = scan.nextLine();
        String city = scan.nextLine();

        AddressDto addressDto =  new AddressDto(country, city);

        addressService.create(addressDto);
    }
}
