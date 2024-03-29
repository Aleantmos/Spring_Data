package bg.softuni.modelmapper.repositories;

import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dto.EmployeeNameAndSalaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    EmployeeNameAndSalaryDto findFirstNameAndSalaryById(long id);
}
