package de.workshops.bookshelf.domain;

import java.util.List;

public class BookTestData {

    public static List<Book> knownBooks() {
        return List.of(
                new Book("Spring Boot 3", "New fancy stuff", "Someone", "123456"),
                new Book("Testing with JUnit", "Old relevant stuff", "Someone else", "654321"),
                new Book("IntelliJ Tips'n'Tricks", "Getting things done faster", "Also someone", "987654")
        );
    }
}
