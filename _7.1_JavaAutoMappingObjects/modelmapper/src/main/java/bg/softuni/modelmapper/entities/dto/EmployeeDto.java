package bg.softuni.modelmapper.entities.dto;

import java.math.BigDecimal;

public class EmployeeDto {
    private String firstName;
    private BigDecimal salary;
    private String addressCity;

    public EmployeeDto() {
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstName='" + firstName + '\'' +
                ", salary=" + salary +
                ", city='" + addressCity + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
}
