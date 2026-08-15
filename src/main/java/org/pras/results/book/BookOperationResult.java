package org.pras.results.book;

import org.pras.models.Book;

public class BookOperationResult {

    private final Book book;
    private final boolean created;

    public BookOperationResult(Book book, boolean created) {
        this.book = book;
        this.created = created;
    }

    public Book getBook() {
        return book;
    }

    public boolean isCreated() {
        return created;
    }
}