package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class BookRestControllerMockMVCTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}", loadContext = true)
    void shouldGetAllBooksFromBookshelf() throws Exception {
        final var mvcResult = mockMvc.perform(get("/book"))
                .andReturn();

        final var payloadAsString = mvcResult.getResponse().getContentAsString();
        List<Book> books = mapper.readValue(payloadAsString, new TypeReference<>() {});

        assertThat(books).hasSize(3);
    }

    @Test
    void shouldCreateNewBook() throws Exception {
        final var mvcResult = mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                            "title": "My first book",
                            "description": "Wish I had written it much earlier",
                            "author": "Birgit Kratz",
                            "isbn": "111-1111111"
                            }
                        """))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}