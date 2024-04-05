package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Astronomer;

@Repository

public interface AstronomerRepository extends JpaRepository<Astronomer, Long> {

    @Query("select count(a) = 0 from Astronomer a " +
            "where a.firstName = :firstName and a.lastName = :lastName")
    boolean nameUniqueness(@Param("firstName") String firstName,
                           @Param("lastName") String lastName);
}
