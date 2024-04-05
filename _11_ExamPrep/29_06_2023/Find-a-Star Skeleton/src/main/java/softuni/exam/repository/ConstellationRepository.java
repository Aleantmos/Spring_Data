package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Constellation;

@Repository
public interface ConstellationRepository extends JpaRepository<Constellation, Long> {

    @Query("select count(c) = 0 from Constellation c where c.name = :name")
    boolean checkIfUniqueByName(@Param("name") String name);
}
