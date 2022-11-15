package de.workshops.bookshelf;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookJdbcTemplateRepository {
    private final NamedParameterJdbcTemplate template;

    public BookJdbcTemplateRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Book> findAllBooks() {
        return template.query("select * from book", new BeanPropertyRowMapper<Book>(Book.class));
    }

    Book saveBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", book.getIsbn());
        params.put("author", book.getAuthor());
        params.put("title", book.getTitle());
        params.put("description", book.getDescription());

        template.update("INSERT INTO book (isbn, title, author, description) VALUES (:isbn, :title, :author, :description)", params);
        return book;
    }
}
