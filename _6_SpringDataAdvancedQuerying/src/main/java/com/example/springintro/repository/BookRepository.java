package com.example.springintro.repository;

import com.example.springintro.model.dto.BookInformation;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /*
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);
    */

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle
            (String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesLessThanAndEditionType (int copies, EditionType editionType);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal min, BigDecimal max);

    List<Book> findAllByReleaseDateNot(LocalDate givenYear);

    List<Book> findAllByReleaseDateBefore(LocalDate givenYear);

    List<Book> findAllByTitleContaining(String contained);

    @Query("select count(b) from Book as b where length(b.title) > :length")
    Long findBooksByTitleLengthLongerThan(Integer length);


    @Query("select new com.example.springintro.model.dto.BookInformation(b.title, b.editionType, b.ageRestriction, b.price)" +
            " from Book b where b.title = :title")
    BookInformation findFirstByTitle(String title);

}
