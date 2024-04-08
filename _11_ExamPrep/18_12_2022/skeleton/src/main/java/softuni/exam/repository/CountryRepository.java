package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {


    @Query("select count(c) = 0 from Country c where c.name = :name")
    Boolean checkNameUniqueness(@Param("name") String name);

    @Query("select count(c) = 0 from Country c where c.code = :code")
    Boolean checkCodeUniqueness(@Param("code") String code);
}
