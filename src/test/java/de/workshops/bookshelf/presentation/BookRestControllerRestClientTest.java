package de.workshops.bookshelf.presentation;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookRestControllerRestClientTest {

    RestClient restClient;

    @SpyBean
    BookService bookService;

    @BeforeEach
    void setUp(@LocalServerPort int port) {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void get_book_returns_all_books() {
        Book[] books = restClient
                .get().uri("/book")
                .retrieve().body(Book[].class);

        assertThat(books).hasSameSizeAs(bookService.getAllBooks());
    }

    @Test
    @DirtiesContext
    void post_book_creates_new_book() {
        Book book = new Book("New Book", "new released book", "Tester", "123123123");
        ResponseEntity<Void> response = restClient
                .post().uri("/book").body(book)
                .retrieve().toBodilessEntity();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(bookService).saveBook(book);
    }
}
