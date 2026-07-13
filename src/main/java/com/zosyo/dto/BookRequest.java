package com.zosyo.dto;

import com.zosyo.entity.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank(message = "タイトルは必須です")
        @Size(max = 30, message = "タイトルは30文字以内で入力してください")
        String title,

        @Size(max = 30, message = "著者名は30文字以内で入力してください")
        String author,

        @Size(max = 30, message = "カテゴリは30文字以内で入力してください")
        String category,

        @Min(value = 0, message = "所有冊数は0以上で入力してください")
        int quantity,

        @Min(value = 0, message = "在庫数は0以上で入力してください")
        int stock,

        @Size(max = 30, message = "備考は30文字以内で入力してください")
        String note
) {
    public Book toEntity() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setQuantity(quantity);
        book.setStock(stock);
        book.setNote(note);
        return book;
    }
}