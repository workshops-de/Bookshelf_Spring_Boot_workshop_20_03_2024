package de.workshops.bookshelf.presentation;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = service.getAllBooks();
        model.addAttribute("books", books);

        return "books_overview";
    }
}
