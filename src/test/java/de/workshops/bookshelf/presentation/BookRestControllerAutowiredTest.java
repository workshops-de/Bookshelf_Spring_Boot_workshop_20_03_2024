package de.workshops.bookshelf.presentation;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static de.workshops.bookshelf.domain.BookTestData.knownBooks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookRestControllerAutowiredTest {

    @MockBean
    BookService bookService;

    @Autowired
    BookRestController bookRestController;

    List<Book> knownBooks = knownBooks();

    @BeforeEach
    void setUp() {
        when(bookService.getAllBooks()).thenReturn(knownBooks);
    }

    @Test
    void getAllBooks_returns_all_books() {
        List<Book> allBooks = bookRestController.getAllBooks();
        assertThat(allBooks).containsExactlyElementsOf(knownBooks);
    }
}
