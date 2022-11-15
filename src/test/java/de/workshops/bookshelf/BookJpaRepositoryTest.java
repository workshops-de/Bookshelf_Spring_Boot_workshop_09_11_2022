package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"classpath:data.sql"})
class BookJpaRepositoryTest {

    @Autowired
    BookJpaRepository repository;

    @Test
    void shouldListAllAuthors() {
        final var authors = repository.findAllAuthors();

        assertThat(authors)
                .hasSize(3)
                .containsExactlyInAnyOrder("Gottfried Wolmeringer", "Erich Gamma", "Robert C. Martin");
    }
}