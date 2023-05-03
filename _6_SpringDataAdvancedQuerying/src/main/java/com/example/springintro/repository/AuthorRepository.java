package com.example.springintro.repository;

import com.example.springintro.model.dto.BookInformation;
import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findByFirstNameEndingWith(String ending);

    List<Author> findByLastNameStartingWith(String element);

    @Query("SELECT a.firstName AS firstName, a.lastName AS lastName, SUM(b.copies) AS totalCopies "+
            "FROM Author a " +
            "JOIN a.books AS b " +
            "GROUP BY b.author " +
            "ORDER BY totalCopies DESC")
    Optional<List<BookInformation>> authorsByBookCopies();
}
