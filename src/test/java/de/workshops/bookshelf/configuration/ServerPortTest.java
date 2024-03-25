package de.workshops.bookshelf.configuration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

class ServerPortTest {

    @Nested
    @SpringBootTest
    class ByDefault {

        @Value("${server.port:8080}")
        int port;

        @Test
        void port_is_8080() {
            assertThat(port).isEqualTo(8080);
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("prod")
    class InProd {

        @Value("${server.port:8080}")
        int port;

        @Test
        void port_is_8090() {
            assertThat(port).isEqualTo(8090);
        }
    }
}
