package bg.softuni.modelmapper.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private AddressDto addressDto;

    public CreateEmployeeDto(String firstName,
                             String lastName,
                             BigDecimal salary,
                             LocalDate birthday,
                             AddressDto addressDto) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.addressDto = addressDto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }
}
