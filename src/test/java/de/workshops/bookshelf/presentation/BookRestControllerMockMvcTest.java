package de.workshops.bookshelf.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static de.workshops.bookshelf.domain.BookTestData.knownBooks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockMvcTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    List<Book> knownBooks = knownBooks();

    @BeforeEach
    void setUp() {
        when(bookService.getAllBooks()).thenReturn(knownBooks);
    }

    @Test
    @WithMockUser
    void get_book_returns_all_books() throws Exception {
        mvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(knownBooks.size())))
                .andExpect(jsonPath("$[1].title", is(knownBooks.get(1).getTitle())));
    }

    @Test
    @WithMockUser
    void get_book_returns_again_all_books() throws Exception {
        MvcResult result = mvc.perform(get("/book")).andReturn();
        MockHttpServletResponse response = result.getResponse();

        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        assertThat(status).isEqualTo(HttpStatus.OK);

        List<Book> allBooks = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(allBooks).hasSameSizeAs(knownBooks);
        assertThat(allBooks.get(1).getTitle()).isEqualTo(knownBooks.get(1).getTitle());
    }
}
