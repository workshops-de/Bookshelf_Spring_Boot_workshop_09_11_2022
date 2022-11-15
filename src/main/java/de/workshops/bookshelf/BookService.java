package de.workshops.bookshelf;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private final BookJpaRepository repository;

    BookService(BookJpaRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAll();
    }

    Book getBookByIsbn(String isbn) {
        final var books = getAllBooks();
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookshelfException("no book with ISBN " + isbn));
    }

    List<Book> getBooksByAuthor(String author) {
        final var books = getAllBooks();
        return books.stream()
                .filter(book -> book.getAuthor().contains(author))
                .toList();
    }

    List<Book> searchBooks(BookSearchRequest searchRequest) {
        final var books = getAllBooks();
        return books.stream()
                .filter(book -> book.getAuthor().contains(searchRequest.author())
                        || book.getIsbn().equals(searchRequest.isbn()))
                .toList();
    }

    public Book createBook(Book book) {
        return repository.save(book);
    }
}
