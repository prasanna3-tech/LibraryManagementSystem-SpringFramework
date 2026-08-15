package org.pras.mappers;

import org.pras.dto.BookResponseDto;
import org.pras.models.Book;
import org.springframework.stereotype.Component;
import java.util.List;


import java.util.ArrayList;


@Component
public class BookResponseMapper {

    public BookResponseDto toResponseDto(Book book) {

        BookResponseDto dto = new BookResponseDto();

        dto.setBookId(book.getBookId());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setIsbn(book.getIsbn());
        dto.setQuantity(book.getQuantity());
        dto.setTitle(book.getTitle());

        return dto;
    }

    public List<BookResponseDto> toResponseDtoList(List<Book> books) {

        List<BookResponseDto> dtoList = new ArrayList<>();

        for (Book book : books) {
            dtoList.add(toResponseDto(book));
        }

        return dtoList;
    }


}
