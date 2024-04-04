package softuni.exam.service;


import softuni.exam.models.entity.Book;

import java.io.IOException;

// TODO: Implement all methods
public interface BookService {

    boolean areImported();

    String readBooksFromFile() throws IOException;

	String importBooks() throws IOException;

    Book getBookByName(String name);
}
