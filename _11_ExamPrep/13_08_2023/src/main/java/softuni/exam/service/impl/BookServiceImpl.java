package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookSeedDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.MyValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOK_FILE_PATH = "src/main/resources/files/json/books.json";
    private final BookRepository bookRepository;
    private final Gson gson;
    private final MyValidation myValidation;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, Gson gson, MyValidation myValidation, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.myValidation = myValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOK_FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readBooksFromFile(), BookSeedDTO[].class))
                .filter(bookSeedDTO -> {
                    boolean filtered = bookFilter(bookSeedDTO);
                    if (filtered) {
                        sb.append(String.format("Successfully imported book %s - %s",
                                bookSeedDTO.getAuthor(), bookSeedDTO.getTitle()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid book")
                                .append(System.lineSeparator());
                    }
                    return filtered;
                })
                .map(bookSeedDTO -> modelMapper.map(bookSeedDTO, Book.class))
                .forEach(bookRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.getBookByTitle(name);
    }

    private boolean bookFilter(BookSeedDTO bookSeedDTO) {
        return myValidation.isValid(bookSeedDTO) && !checkTitleUniqueness(bookSeedDTO.getTitle());
    }

    private boolean checkTitleUniqueness(String title) {
        return bookRepository.existsByTitle(title);
    }
}
