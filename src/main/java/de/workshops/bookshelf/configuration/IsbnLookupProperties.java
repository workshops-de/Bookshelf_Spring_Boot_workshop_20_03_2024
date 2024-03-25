package de.workshops.bookshelf.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class IsbnLookupProperties {

    private URI url;

    private String apiKey;

    @Min(1)
    @Max(5)
    private int maxRetry = 1;
}
