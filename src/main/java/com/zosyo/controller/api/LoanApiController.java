package com.zosyo.controller.api;

import com.zosyo.dto.LoanRequest;
import com.zosyo.dto.LoanResponse;
import com.zosyo.entity.Loan;
import com.zosyo.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanApiController {

    private final LoanService loanService;

    public LoanApiController(LoanService loanService) {
        this.loanService = loanService;
    }

    // 貸出中の書籍一覧の取得
    @GetMapping
    public List<LoanResponse> list() {
        return loanService.findActiveLoans().stream()
                .map(LoanResponse::from)
                .toList();
    }

    // 貸出登録
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse create(@Valid @RequestBody LoanRequest request) {
        Loan loan = new Loan();
        loan.setBorrowerName(request.borrowerName());
        loan.setDueDate(request.dueDate());
        loanService.loanBook(request.bookId(), loan);
        return LoanResponse.from(loan);
    }

    // 返却処理
    @PostMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable Long id) {
        return LoanResponse.from(loanService.returnBook(id));
    }
}