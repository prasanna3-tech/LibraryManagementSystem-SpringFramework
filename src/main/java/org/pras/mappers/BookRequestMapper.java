package org.pras.mappers;

import org.pras.dto.bookDtos.BookRequestDto;
import org.pras.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookRequestMapper {

    public Book toEntity(BookRequestDto dto) {

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setQuantity(dto.getQuantity());

        return book;
    }
}