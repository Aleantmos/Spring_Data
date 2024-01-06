package com.jsonex.repostitories;

import com.jsonex.domain.entities.Category;
import com.jsonex.domain.entities.Product;
import com.jsonex.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select * from `product-shop`.categories order by RAND () LIMIT 1", nativeQuery = true)
    Optional<Category> getRandomEntity();

}
