package de.workshops.bookshelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<Book>> getAllBooks() {
        final var books = service.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("{isbn}")
    ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        final var book = service.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @GetMapping(params = "author")
    ResponseEntity<List<Book>> getBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) {
        final var books = service.getBooksByAuthor(author);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    ResponseEntity<List<Book>> searchBook(@RequestBody BookSearchRequest searchRequest) {
        final var books = service.searchBooks(searchRequest);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping
    ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.created(null).body(book);
    }
}
