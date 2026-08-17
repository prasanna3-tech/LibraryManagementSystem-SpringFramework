package org.pras.dto.bookDtos;

public class BookResponseDto {
    private int bookId;
    private String author;
    private String category;
    private String isbn;
    private int quantity;
    private String title;

    public void setBookId(int bookId) {
        this.bookId=bookId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }
}
