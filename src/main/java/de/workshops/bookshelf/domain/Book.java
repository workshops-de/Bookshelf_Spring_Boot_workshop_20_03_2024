package de.workshops.bookshelf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private String title;
    private String description;
    private String author;
    private String isbn;

}
