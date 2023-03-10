package softuni.bookshop.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.bookshop.model.entity.Book;
import softuni.bookshop.service.AuthorService;
import softuni.bookshop.service.BookService;
import softuni.bookshop.service.CategoryService;

import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.addCategories();
        authorService.addAuthors();
        bookService.addBooks();

        //printAllBooksAfter2000(2000);

        //printAuthorFirstNameAndAuthorLastNameWithBookReleasedBefore1990(1990);

        //printAllAuthorsFullNameAndNumberOfBooksReleased();

        printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsFullNameAndNumberOfBooksReleased() {

        authorService
                .getAllAuthorsOrderByBooksCount()
                .forEach(System.out::println);
    }

    private void printAuthorFirstNameAndAuthorLastNameWithBookReleasedBefore1990(int year) {
        bookService.findAllAuthorsWithBooksWithReleaseDateBefore(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfter2000(int year) {
        bookService.findBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }
}
