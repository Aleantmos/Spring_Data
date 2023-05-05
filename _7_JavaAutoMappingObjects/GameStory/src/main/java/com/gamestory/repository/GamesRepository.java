package com.gamestory.repository;

import com.gamestory.domain.entity.Game;
import com.gamestory.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {
}
