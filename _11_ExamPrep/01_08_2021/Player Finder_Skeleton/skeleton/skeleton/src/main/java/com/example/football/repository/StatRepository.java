package com.example.football.repository;

import com.example.football.models.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("select count(s) = 0 from Stat s " +
            "where s.passing = :passing " +
            "and s.endurance = :endurance " +
            "and s.shooting = :shooting")
    boolean checkStatUniqueness(@Param("passing") Float passing,
                                @Param("shooting") Float shooting,
                                @Param("endurance") Float endurance);
}
