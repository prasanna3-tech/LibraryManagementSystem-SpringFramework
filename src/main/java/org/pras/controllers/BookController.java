package org.pras.controllers;

import java.util.List;
import org.pras.dto.BookRequestDto;
import org.pras.dto.BookResponseDto;
import org.pras.mappers.BookRequestMapper;
import org.pras.mappers.BookResponseMapper;
import org.pras.models.Book;
import org.pras.results.book.BookOperationResult;
import org.pras.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final BookResponseMapper bookResponseMapper;

    private final BookRequestMapper bookRequestMapper;

    public BookController(
            BookService bookService,
            BookResponseMapper bookResponseMapper,
            BookRequestMapper bookRequestMapper) {

        this.bookService = bookService;
        this.bookResponseMapper = bookResponseMapper;
        this.bookRequestMapper = bookRequestMapper;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> searchBookById(@PathVariable("bookId") int bookId) {

        Book book = bookService.searchBookById(bookId);

        BookResponseDto dto = bookResponseMapper.toResponseDto(book);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(
            @RequestBody BookRequestDto request) {

        Book book = bookRequestMapper.toEntity(request);

        BookOperationResult result = bookService.addBook(book);

        BookResponseDto response =
                bookResponseMapper.toResponseDto(result.getBook());

        if (result.isCreated()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> searchBooksByKeyword(
            @RequestParam("keyword") String keyword) {

        List<Book> books =
                bookService.searchBooksByKeyword(keyword);

        List<BookResponseDto> response =
                books.stream()
                        .map(bookResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> removeBook(
            @PathVariable int bookId) {

        Book deletedBook = bookService.removeBook(bookId);

        BookResponseDto response =
                bookResponseMapper.toResponseDto(deletedBook);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable int bookId,
            @RequestBody BookRequestDto request) {

        Book book = bookRequestMapper.toEntity(request);
        book.setBookId(bookId);

        Book updatedBook = bookService.updateBook(book);

        BookResponseDto response =
                bookResponseMapper.toResponseDto(updatedBook);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {

        List<Book> books = bookService.displayAllBooks();

        List<BookResponseDto> response =
                books.stream()
                        .map(bookResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}