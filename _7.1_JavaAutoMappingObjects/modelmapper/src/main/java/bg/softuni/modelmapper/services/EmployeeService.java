package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dto.CreateEmployeeDto;
import bg.softuni.modelmapper.entities.dto.EmployeeDto;
import bg.softuni.modelmapper.entities.dto.EmployeeNameAndSalaryDto;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDto createEmployeeDto);

    List<EmployeeDto> findAll();

    EmployeeNameAndSalaryDto findNamesById(long id);
}
