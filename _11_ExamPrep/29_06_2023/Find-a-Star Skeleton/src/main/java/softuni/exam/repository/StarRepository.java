package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.StarProjection;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;

@Repository

public interface StarRepository extends JpaRepository<Star, Long> {

    @Query("select count(s) = 0 from Star s where s.name = :name")
    boolean checkNameUniqueness(@Param("name") String name);

    @Query("select s from Star s where s.id = :id")
    Optional<Star> getStarById(@Param("id") Long id);

    // starName, lightYears, description, constellationName

    @Query("select s.name as starName, " +
            "s.lightYears as lightYears, " +
            "s.description as description, " +
            "c.name as constellationName " +
            "from Star s " +
            "left join Constellation c on s.constellation.id = c.id " +
            "where s.starType = 'RED_GIANT' " +
            "and not exists(" +
            "select 1 from Astronomer a " +
            "where a.observingStar.id = s.id" +
            ") order by s.lightYears asc")
    List<StarProjection> exportNonObservedStars();
}

