package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;

import java.util.Optional;

@Repository

public interface StarRepository extends JpaRepository<Star, Long> {

    @Query("select count(s) = 0 from Star s where s.name = :name")
    boolean checkNameUniqueness(@Param("name") String name);

    @Query("select s from Star s where s.id = :id")
    Optional<Star> getStarById(@Param("id") Long id);
}
