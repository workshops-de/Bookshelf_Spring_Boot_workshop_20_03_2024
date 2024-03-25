package de.workshops.bookshelf.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("bookshelf")
@Validated
@Getter
@Setter
public class BookshelfProperties {

    private String owner;

    @NestedConfigurationProperty
    private IsbnLookupProperties isbnLookup = new IsbnLookupProperties();
}
