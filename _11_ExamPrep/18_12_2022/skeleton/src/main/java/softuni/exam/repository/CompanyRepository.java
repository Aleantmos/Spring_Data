package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.companyDTO.CompanySeedDTO;
import softuni.exam.models.entity.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select count(c) = 0 from Company c where c.name = :name")
    boolean checkUniqueness(@Param("name") String name);

    @Query("select c from Company c where c.id = :companyId")
    Optional<Company> getCompanyById(@Param("companyId") Long companyId);
}
