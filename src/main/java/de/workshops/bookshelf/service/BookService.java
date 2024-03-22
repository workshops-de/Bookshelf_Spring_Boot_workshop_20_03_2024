package de.workshops.bookshelf.service;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.persistence.BookRepository;
import de.workshops.bookshelf.domain.BookSearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        return repository.getAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn)).findFirst()
                .orElseThrow(() -> new BookNotFoundException("ISBN " + isbn));
    }

    public Book searchBookByAuthor(String author) {
        return repository.getAllBooks().stream()
                .filter(book -> hasAuthor(book, author)).findFirst()
                .orElseThrow(() -> new BookNotFoundException("Author '" + author + "'"));
    }

    public List<Book> searchBooks(BookSearchRequest searchRequest) {
        return repository.getAllBooks().stream()
                .filter(book -> searchRequest.getIsbn() == null || hasIsbn(book, searchRequest.getIsbn()))
                .filter(book -> searchRequest.getAuthor() == null || hasAuthor(book, searchRequest.getAuthor()))
                .collect(toList());
    }

    public void saveBook(Book book) {
        repository.saveBook(book);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
