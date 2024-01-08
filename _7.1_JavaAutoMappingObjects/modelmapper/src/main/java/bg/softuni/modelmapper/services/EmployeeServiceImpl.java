package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dto.CreateEmployeeDto;
import bg.softuni.modelmapper.entities.dto.EmployeeDto;
import bg.softuni.modelmapper.entities.dto.EmployeeNameAndSalaryDto;
import bg.softuni.modelmapper.repositories.AddressRepository;
import bg.softuni.modelmapper.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Employee create(CreateEmployeeDto createEmployeeDto) {
        Employee employee = modelMapper.map(createEmployeeDto, Employee.class);

        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                createEmployeeDto.getAddressDto().getCountry(),
                createEmployeeDto.getAddressDto().getCity());

        address.ifPresent(employee::setAddress);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return this.employeeRepository.findAll()
                .stream()
                .map( e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeNameAndSalaryDto findNamesById(long id) {
        return this.employeeRepository.findFirstNameAndSalaryById(id);
    }
}
