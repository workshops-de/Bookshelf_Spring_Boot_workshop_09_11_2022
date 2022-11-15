package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookJdbcTemplateRepository.class})
@Sql({"classpath:data.sql"})
class BookJdbcTemplateRepositoryTest {

    @Autowired
    BookJdbcTemplateRepository repository;

    @Test
    void shouldGetAllBooks () {
        final var allBooks = repository.findAllBooks();
        assertThat(allBooks).hasSize(3);
    }

    @Test
    void shouldInsertNewBook () {
        final var book = new Book();
        book.setIsbn("11111111");
        book.setAuthor("Birgit Kratz");
        book.setTitle("My famous book");
        book.setDescription("Written with sweat and tears");

        final var savedBook = repository.saveBook(book);

        assertThat(repository.findAllBooks()).hasSize(4);
    }
}