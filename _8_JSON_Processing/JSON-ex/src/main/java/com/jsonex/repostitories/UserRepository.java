package com.jsonex.repostitories;

import com.jsonex.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from `product-shop`.categories order by RAND () LIMIT 1", nativeQuery = true)
    Optional<User> getRandomEntity();

}
