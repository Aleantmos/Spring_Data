package softuni.bookshop.service;

import org.springframework.stereotype.Service;
import softuni.bookshop.model.entity.Author;
import softuni.bookshop.repository.AuthorRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public Author getRandomAuthor() {
        Long randomId = ThreadLocalRandom
                .current()
                .nextLong(0, authorRepository.count());

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    public void addAuthors() throws IOException {

        if (authorRepository.count() == 0) {
            Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                    .forEach(row -> {
                        String[] fullName = row.split(" ");
                        Author author = new Author();
                        author.setFirstName(fullName[0]);
                        author.setLastName(fullName[1]);

                        authorRepository.save(author);
                    });
        }
    }

    public List<String> getAllAuthorsOrderByBooksCount() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d", author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
