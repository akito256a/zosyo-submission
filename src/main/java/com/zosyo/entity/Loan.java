package com.zosyo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotBlank(message = "貸出者名は必須です")
    @Size(max = 30, message = "貸出者名は30文字以内で入力してください")
    @Column(name = "borrower_name", nullable = false, length = 100)
    private String borrowerName;

    @Column(name = "loaned_at", nullable = false, updatable = false)
    private LocalDateTime loanedAt;

    @NotNull(message = "返却期限は必須です")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @PrePersist
    protected void onCreate() {
        loanedAt = LocalDateTime.now();
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public LocalDateTime getLoanedAt() { return loanedAt; }
    public void setLoanedAt(LocalDateTime loanedAt) { this.loanedAt = loanedAt; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
}