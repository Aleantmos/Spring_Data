package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.enums.EditionType;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    /*void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);*/

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllGoldenEditionBooks(int copiesCnt, EditionType editionType);

    List<Book> findBooksWithPriceNotBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findBooksNotReleasedInYear(LocalDate date);

    List<Book> findBooksReleasedBefore(LocalDate formatted);

    List<Book> findBooksContaining(String partContained);
}
