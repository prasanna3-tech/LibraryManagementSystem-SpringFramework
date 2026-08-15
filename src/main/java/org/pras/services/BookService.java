package org.pras.services;
import org.pras.exceptions.BookCurrentlyBorrowedException;
import org.pras.exceptions.BookNotFoundException;
import org.pras.repositories.BorrowRecordRepository;
import org.pras.results.book.BookOperationResult;
import org.springframework.transaction.annotation.Transactional;
import org.pras.models.Book;
import org.pras.models.BorrowRecord;
import java.util.List;
import java.util.Optional;
import org.pras.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BookService(
            BookRepository bookRepository,
            BorrowRecordRepository borrowRecordRepository) {

        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Transactional
    public BookOperationResult addBook(Book newBook) {

        Optional<Book> existingBook =
                bookRepository.findByIsbn(newBook.getIsbn());

        if (existingBook.isPresent()) {

            Book book = existingBook.get();

            book.setQuantity(
                    book.getQuantity() + newBook.getQuantity()
            );

            return new BookOperationResult(book, false);

        } else {

            bookRepository.save(newBook);

            return new BookOperationResult(newBook, true);

        }
    }

    public Book searchBookById(int bookId) {

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<Book> searchBooksByKeyword(String keyword) {
        return bookRepository.searchByKeyword(keyword);
    }

    @Transactional
    public Book removeBook(int bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        Optional<BorrowRecord> borrowRecord =
                borrowRecordRepository
                        .findByBookBookIdAndReturnedFalse(bookId);

        if (borrowRecord.isPresent()) {
            throw new BookCurrentlyBorrowedException(bookId);
        }

        bookRepository.delete(book);

        return book;
    }

    @Transactional
    public Book updateBook(Book updatedBook) {


        Book book = bookRepository.findById(updatedBook.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(updatedBook.getBookId()));

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setCategory(updatedBook.getCategory());
        book.setQuantity(updatedBook.getQuantity());

        return book;
    }

    public List<Book> displayAllBooks() {
        return bookRepository.findAll();
    }
}