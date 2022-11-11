package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRestControllerPlainJavaMockitoTest {

    @Mock
    BookService service;

    @InjectMocks
    BookRestController controller;

    @Test
    void shouldGetBooByIsbn() {
        final var expectedBook = new Book();
        expectedBook.setIsbn("1111111");
        when(service.getBookByIsbn(anyString())).thenReturn(expectedBook);

        final var bookByIsbn = controller.getBookByIsbn("1111111").getBody();

        assertThat(bookByIsbn).hasFieldOrPropertyWithValue("isbn", "1111111");
    }
}