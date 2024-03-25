package de.workshops.bookshelf.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SwaggerConfigurationTest {

    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc
    class ByDefault {

        @Autowired
        MockMvc mvc;

        @Autowired
        OpenAPI configuration;

        @Test
        void swagger_is_available() throws Exception {
            mvc.perform(get("/swagger-ui/index.html"))
                    .andExpect(status().isOk());
        }

        @Test
        void configuration_is_present() {
            assertThat(configuration.getInfo().getTitle()).isEqualTo("Bookshelf API");
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("prod")
    @AutoConfigureMockMvc
    class InProd {

        @Autowired
        MockMvc mvc;

        @Autowired
        ObjectProvider<OpenAPI> configuration;

        @Test
        void swagger_is_not_available() throws Exception {
            mvc.perform(get("/swagger-ui/index.html"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void configuration_is_absent() {
            assertThat(configuration).isEmpty();
        }
    }
}
