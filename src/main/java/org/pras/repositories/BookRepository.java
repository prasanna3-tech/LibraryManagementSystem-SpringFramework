package org.pras.repositories;

import org.pras.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
    @Query("""
    SELECT b
    FROM Book b
    WHERE b.title LIKE CONCAT('%', :keyword, '%')
       OR b.author LIKE CONCAT('%', :keyword, '%')
       OR b.category LIKE CONCAT('%', :keyword, '%')
    """)
    List<Book> searchByKeyword(@Param("keyword") String keyword);
}
