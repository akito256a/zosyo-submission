package com.zosyo.service;

import com.zosyo.entity.Book;
import com.zosyo.entity.Loan;
import com.zosyo.exception.AlreadyReturnedException;
import com.zosyo.exception.InsufficientStockException;
import com.zosyo.exception.ResourceNotFoundException;
import com.zosyo.repository.BookRepository;
import com.zosyo.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Transactional(readOnly = true)
    public List<Loan> findAll() {
        return loanRepository.findAllByOrderByLoanedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Loan> findByBookId(Long bookId) {
        return loanRepository.findByBookIdOrderByLoanedAtDesc(bookId);
    }

    // 貸出中のみ取得：API用です
    @Transactional(readOnly = true)
    public List<Loan> findActiveLoans() {
        return loanRepository.findByReturnDateIsNullOrderByLoanedAtDesc();
    }

    // 貸出処理：悲観ロックで在庫を取得してからチェック
    public void loanBook(Long bookId, Loan loan) {
        Book book = bookRepository.findByIdForUpdate(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("書籍が見つかりません。ID: " + bookId));

        if (book.getStock() <= 0) {
            throw new InsufficientStockException("在庫がないため貸出できません。");
        }

        book.setStock(book.getStock() - 1);
        loan.setBook(book);
        loanRepository.save(loan);
    }

    // 返却処理：削除ではなくreturnDateを更新し、更新後のLoanを返す処理です
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("貸出記録が見つかりません。ID: " + loanId));

        if (loan.isReturned()) {
            throw new AlreadyReturnedException("この貸出はすでに返却済みです。");
        }

        Book book = bookRepository.findByIdForUpdate(loan.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("書籍が見つかりません。"));

        book.setStock(book.getStock() + 1);
        loan.setReturnDate(LocalDateTime.now());
        return loan;
    }
}