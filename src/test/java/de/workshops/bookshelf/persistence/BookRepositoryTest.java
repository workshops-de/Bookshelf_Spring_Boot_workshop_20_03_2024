package de.workshops.bookshelf.persistence;

import de.workshops.bookshelf.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class BookRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    BookRepository repository;

    @Test
    void getAllBooks_returns_a_list_of_books() {
        List<Book> books = repository.findAll();

        assertThat(books).isNotEmpty().doesNotContainNull();
    }

    @Test
    void saveBook_saves_book_in_database() {
        List<Book> booksBefore = repository.findAll();

        repository.save(new Book("Titel", "Beschreibung", "Autor", "ISBN"));

        List<Book> booksAfter = repository.findAll();
        assertThat(booksAfter).hasSize(booksBefore.size() + 1);
    }
}
