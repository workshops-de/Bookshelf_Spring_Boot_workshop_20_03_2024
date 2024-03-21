package de.workshops.bookshelf.domain;

import lombok.Data;

@Data
public class BookSearchRequest {

    private String author;
    private String isbn;
}
