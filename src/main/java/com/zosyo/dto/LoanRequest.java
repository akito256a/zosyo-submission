package com.zosyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record LoanRequest(
        @JsonProperty("book_id")
        @NotNull(message = "書籍IDは必須です")
        Long bookId,

        @JsonProperty("borrower_name")
        @NotBlank(message = "貸出者名は必須です")
        @Size(max = 30, message = "貸出者名は30文字以内で入力してください")
        String borrowerName,

        @JsonProperty("due_date")
        @NotNull(message = "返却期限は必須です")
        LocalDate dueDate
) {}