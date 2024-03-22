package de.workshops.bookshelf.presentation;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.domain.BookSearchRequest;
import de.workshops.bookshelf.service.BookService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/book")
@Validated
@Slf4j
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) {
        return service.getBookByIsbn(isbn);
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) {
        return service.searchBookByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest searchRequest) {
        return service.searchBooks(searchRequest);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveBook(@RequestBody Book book) {
        service.saveBook(book);
    }

    @PostMapping("/upload")
    public void uploadBook(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file with name {} ...", file.getOriginalFilename());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(BookNotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body("Book not found: " + e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(ConstraintViolationException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(e.getMessage());
    }
}
