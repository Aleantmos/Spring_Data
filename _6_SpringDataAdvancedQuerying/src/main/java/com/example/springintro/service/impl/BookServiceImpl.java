package com.example.springintro.service.impl;

import com.example.springintro.model.dto.BookInformation;
import com.example.springintro.model.entity.*;
import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.enums.EditionType;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

  /*  @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }*/

    @Override
    public List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> findAllGoldenEditionBooks(int copiesCnt, EditionType editionType) {
        return bookRepository.findAllByCopiesLessThanAndEditionType(copiesCnt, editionType);
    }

    public List<Book> findBooksWithPriceNotBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(minPrice, maxPrice);
    }

    @Override
    public List<Book> findBooksNotReleasedInYear(LocalDate date) {
        return bookRepository.findAllByReleaseDateNot(date);
    }

    @Override
    public List<Book> findBooksReleasedBefore(LocalDate formatted) {
        return bookRepository.findAllByReleaseDateBefore(formatted);
    }

    @Override
    public List<Book> findBooksContaining(String partContained) {
        return bookRepository.findAllByTitleContaining(partContained);
    }

    @Override
    public Long findBooksWithTitleLonger(Integer length) {
        return bookRepository.findBooksByTitleLengthLongerThan(length);
    }

    @Override
    public BookInformation findFirstByTitle(String title) {
        return bookRepository.findFirstByTitle(title);
    }

    @Override
    public int increaseBookCopies(LocalDate date, int copies) {
        return bookRepository.increaseBookCopies(copies, date);
    }

    @Override
    public int deleteAllByCopiesLessThan(Integer copies) {
        return bookRepository.deleteAllByCopiesLessThan(copies);
    }


}
