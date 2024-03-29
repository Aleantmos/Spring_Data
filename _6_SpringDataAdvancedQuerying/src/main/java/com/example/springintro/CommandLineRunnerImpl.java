package com.example.springintro;

import com.example.springintro.model.dto.BookInformation;
import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.enums.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    private final Scanner scanner;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        //booksTitlesByAgeRestriction();

        //goldenBooks();

        //booksPriceWithBetween();

        //booksNotReleasedInYear();

        //booksReleasedBeforeDate();

        //findAuthorsByNameEnding();

        //findBooksByNameContaining();

        //findBooksOfAuthorsWithLastNameContaining();

        //findCountOfBooksWhoseTitleIsLongerThan();

        //findTotalBookCopiesForAllAuthors();

        //findFirstBookByTitle();

        //increaseBookCopiesOfBooksReleasedAfter() -> notFinished;

        removeBooksWithCopiesLowerThan();
    }

    private void removeBooksWithCopiesLowerThan() {
        final int copies = scanner.nextInt();

        System.out.println(bookService.deleteAllByCopiesLessThan(copies));
    }

    private void increaseBookCopiesOfBooksReleasedAfter() {
        // todo

    }

    private void findFirstBookByTitle() {
        String title = scanner.nextLine();
        BookInformation firstByTitle = bookService.findFirstByTitle(title);
        System.out.println(firstByTitle.toString());
    }



    private void findCountOfBooksWhoseTitleIsLongerThan() {
        Integer length = Integer.parseInt(scanner.nextLine());

        Long cnt = bookService.findBooksWithTitleLonger(length);

        System.out.printf("There are %d books with longer title than %d symbols.", cnt, length );

    }

    private void findBooksOfAuthorsWithLastNameContaining() {

        String elementContained = scanner.nextLine();

        List<Author> authors = authorService.authorsLastNameStartsWith(elementContained);

        String collected = authors.stream()
                .map(author -> author.getBooks()
                        .stream()
                        .map(book -> String.format("%s (%s)", book.getTitle(), author.getLastName()))
                        .collect(Collectors.joining(System.lineSeparator())))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);

    }

    private void findBooksByNameContaining() {

        String partContained = scanner.nextLine();

        List<Book> booksContaining = bookService.findBooksContaining(partContained);

        String collected = booksContaining.stream()
                .map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);
    }

    private void findAuthorsByNameEnding() {
        String ending = scanner.nextLine();

        List<Author> authorByNameEndingWith = authorService.findAuthorByNameEndingWith(ending);

        String collect = authorByNameEndingWith.stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collect);
    }

    private void booksReleasedBeforeDate() {
        final String date = scanner.nextLine();

        String[] dateInfo = date.split("-");

        LocalDate formatted = LocalDate.of(Integer.parseInt(dateInfo[2]), Integer.parseInt(dateInfo[1]), Integer.parseInt(dateInfo[0]));

        List<Book> releasedBefore = bookService.findBooksReleasedBefore(formatted);

        String collected = releasedBefore.stream()
                .map(book -> String.format("%s %s %.2f", book.getTitle(), book.getEditionType().toString(), book.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);

    }

    private void booksNotReleasedInYear() {
        int year = Integer.parseInt(scanner.nextLine());

        LocalDate date = LocalDate.of(year, 1, 1);

        List<Book> booksNotReleasedInYear = bookService.findBooksNotReleasedInYear(date);

        String collect = booksNotReleasedInYear.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collect);
    }

    private void booksPriceWithBetween() {
        final BigDecimal minPrice = BigDecimal.valueOf(5L);
        final BigDecimal maxPrice = BigDecimal.valueOf(40L);

        List<Book> booksWithPriceNotBetween = bookService.findBooksWithPriceNotBetween(minPrice, maxPrice);

        String collected = booksWithPriceNotBetween.stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);
    }

    private void goldenBooks() {
        final int copiesCnt = 5000;

        final String editionTypeString = "Gold";

        EditionType editionType = EditionType.valueOf(editionTypeString.toUpperCase());

        List<Book> allGoldenEditionBooks = bookService.findAllGoldenEditionBooks(copiesCnt, editionType);

        String collected = allGoldenEditionBooks.stream().map(Book::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);

    }

    public void booksTitlesByAgeRestriction() {
        final String ageRestrictionType = scanner.nextLine().toUpperCase();

        AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestrictionType);

        List<Book> allByAgeRestriction = bookService.findAllByAgeRestriction(ageRestriction);

        String collected = allByAgeRestriction.stream().map(Book::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(collected);
    }


}

