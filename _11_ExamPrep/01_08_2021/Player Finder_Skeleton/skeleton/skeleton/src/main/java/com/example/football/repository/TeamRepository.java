package com.example.football.repository;

import com.example.football.models.dto.TeamSeedDTO;
import com.example.football.models.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select count(t) = 0 from Team t where t.name = :name")
    Boolean checkNameUniqueness(@Param("name") String name);

    @Query("select t from Team t where t.name = :name")
    Team getTeamByName(@Param("name") String name);
}
