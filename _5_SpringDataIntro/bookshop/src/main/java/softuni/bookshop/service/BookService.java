package softuni.bookshop.service;

import org.springframework.stereotype.Service;
import softuni.bookshop.model.entity.Author;
import softuni.bookshop.model.entity.Book;
import softuni.bookshop.model.entity.Category;
import softuni.bookshop.model.enums.AgeRestriction;
import softuni.bookshop.model.enums.EditionType;
import softuni.bookshop.repository.BookRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final String BOOK_FILES_PATH = "src/main/resources/files/books.txt";
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookService(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    public void addBooks() throws IOException {


        if (bookRepository.count() > 0) {
            return;
        }


        Files.readAllLines(Path.of(BOOK_FILES_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBook(bookInfo);

                    bookRepository.save(book);
                });

    }

    public List<Book> findBooksAfterYear(int year) {
        return bookRepository
                .findBooksByReleaseDateAfter(LocalDate.of(year, 1, 1));

    }

    private Book createBook(String[] bookInfo) {

        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];

        LocalDate releaseDate = LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));

        Long copies = Long.parseLong(bookInfo[2]);

        BigDecimal price = new BigDecimal(bookInfo[3]);

        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];

        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService
                .getRandomCategories();


        return new Book()
                .setEditionType(editionType)
                .setReleaseDate(releaseDate)
                .setCopies(copies)
                .setPrice(price)
                .setAgeRestriction(ageRestriction)
                .setTitle(title)
                .setAuthor(author)
                .setCategories(categories);

    }

    public List<String> findAllAuthorsWithBooksWithReleaseDateBefore(int year) {
        List<Book> books = bookRepository.findBooksByReleaseDateBefore(LocalDate.of(year, 1, 1));

        return books.stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());

    }

    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d", book.getTitle(), book.getReleaseDate().toString(), book.getCopies()))
                .collect(Collectors.toList());
    }
}
