package com.example.football.repository;

import com.example.football.models.dto.BestPlayersProjection;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select count(p) = 0 from Player p where p.email = :email")
    boolean playerEmailUniqueness(@Param("email") String email);

    @Query("select p.firstName as firstName, p.lastName as lastName, " +
            "p.position as positionName, " +
            "te.name as teamName, " +
            "te.stadiumName as stadiumName from Player p " +
            "left join Team te on p.team.id = te.id " +
            "left join Stat s on p.stat.id = s.id " +
            "where p.birthdate > '1995-01-01' " +
            "and p.birthdate < '2003-01-01' " +
            "order by s.shooting desc, s.passing desc, s.endurance desc, p.lastName")
    List<BestPlayersProjection> exportBestPlayers();
}
