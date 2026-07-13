package com.zosyo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zosyo.entity.Loan;
import java.time.LocalDateTime;

public record LoanResponse(
        Long id,

        @JsonProperty("book_id")
        Long bookId,

        String title,

        @JsonProperty("borrower_name")
        String borrowerName,

        @JsonProperty("loan_date")
        LocalDateTime loanDate,

        @JsonProperty("return_date")
        LocalDateTime returnDate
) {
    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getBorrowerName(),
                loan.getLoanedAt(),
                loan.getReturnDate()
        );
    }
}