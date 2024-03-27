package de.workshops.bookshelf.presentation;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static de.workshops.bookshelf.domain.BookTestData.knownBooks;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

class BookRestControllerRestAssuredTest {

    List<Book> knownBooks = knownBooks();

    @Nested
    @SpringBootTest
    class WithMockMvc {

        @Autowired
        BookRestController bookRestController;

        @MockBean
        BookService bookService;

        @BeforeEach
        void setUp() {
            when(bookService.getAllBooks()).thenReturn(knownBooks);
        }

        @Test
        void get_book_returns_all_books() {
            RestAssuredMockMvc.standaloneSetup(bookRestController);

            RestAssuredMockMvc
                    .when()
                        .get("/book")
                    .then()
                        .statusCode(200)
                        .body("author[0]", equalTo(knownBooks.get(0).getAuthor()));
        }
    }

    @Nested
    @SpringBootTest(webEnvironment = RANDOM_PORT)
    class WithHttp {

        @MockBean
        BookService bookService;

        @BeforeEach
        void setUp(@LocalServerPort int port) {
            RestAssured.port = port;

            when(bookService.getAllBooks()).thenReturn(knownBooks);
        }

        @Test
        void get_book_returns_all_books() {
            RestAssured.given()
                        .auth().basic("dbUser", "password") // TODO don't user real user here
                    .when()
                        .get("/book")
                    .then()
                        .statusCode(200)
                        .body("author[0]", equalTo(knownBooks.get(0).getAuthor()));
        }
    }
}
