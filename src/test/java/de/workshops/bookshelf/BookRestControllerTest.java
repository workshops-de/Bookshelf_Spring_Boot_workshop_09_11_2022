package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    BookRestController controller;
    @Test
    void shouldGetAllBooksFromBookshelf() {
        final var allBooks = controller.getAllBooks().getBody();

        assertThat(allBooks).hasSize(3);
    }
}