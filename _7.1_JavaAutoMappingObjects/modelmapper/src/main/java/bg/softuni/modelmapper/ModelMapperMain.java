package bg.softuni.modelmapper;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;


public class ModelMapperMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

//        PropertyMap<Employee, EmployeeDto> propertyMap = new PropertyMap<>() {
//
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity());
//            }
//        };
//
//        modelMapper.addMappings(propertyMap);

        TypeMap<Employee, EmployeeDto> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDto.class);
        typeMap.addMappings(mapping -> mapping.map(source ->
                        source.getAddress().getCity(),
                (destination, value) -> destination.setAddressCity(value.toString())));
//        typeMap.validate();

        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN, address);

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

    }
}
