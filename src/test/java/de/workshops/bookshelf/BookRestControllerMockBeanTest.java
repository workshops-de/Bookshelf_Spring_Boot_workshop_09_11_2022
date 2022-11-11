package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
//@SpringBootTest
//@AutoConfigureMockMvc
class BookRestControllerMockBeanTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService service;

    @Captor
    ArgumentCaptor<String> isbnCaptor;

    @Test
    void shouldGetBookByIsbn() throws Exception {
        var expectedBook = new Book();
        expectedBook.setIsbn("111111");
        when(service.getBookByIsbn(isbnCaptor.capture())).thenReturn(expectedBook);

        final var mvcResult = mockMvc.perform(get("/book/111111"))
                .andReturn();

        final var payloadAsString = mvcResult.getResponse().getContentAsString();
        Book book = mapper.readValue(payloadAsString, new TypeReference<>() {});

        assertThat(book).hasFieldOrPropertyWithValue("isbn", "111111");

        assertThat(isbnCaptor.getValue()).isEqualTo("111111");
    }
}