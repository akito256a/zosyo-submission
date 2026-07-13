package com.zosyo.dto;

import com.zosyo.entity.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String category,
        int quantity,
        int stock,
        String note
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getQuantity(),
                book.getStock(),
                book.getNote()
        );
    }
}