package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import softuni.exam.models.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select count(p) = 0 from Person p where p.email = :email" )
    Boolean emailUniqueness(@Param("email") String email);

    @Query("select count(p) = 0 from Person p where p.phone = :phone")
    Boolean phoneUniqueness(@Param("phone") String phone);
}
