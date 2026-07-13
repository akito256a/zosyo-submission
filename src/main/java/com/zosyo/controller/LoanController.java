package com.zosyo.controller;

import com.zosyo.entity.Book;
import com.zosyo.entity.Loan;
import com.zosyo.service.BookService;
import com.zosyo.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;

    // コンストラクタインジェクション(DI)
    public LoanController(LoanService loanService, BookService bookService) {
        this.loanService = loanService;
        this.bookService = bookService;
    }

    // ===========================
    // 貸出一覧表示
    // ===========================
    @GetMapping
    public String index(Model model) {
        model.addAttribute("loans", loanService.findAll());
        return "loans/list";
    }

    // ===========================
    // 貸出フォーム表示
    // ===========================
    @GetMapping("/new")
    public String newLoan(@RequestParam Long bookId, Model model) {
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("loan", new Loan());
        return "loans/form";
    }

    // ===========================
    // 貸出処理
    // ===========================
    @PostMapping
    public String create(
            @RequestParam Long bookId,
            @Valid @ModelAttribute Loan loan,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("book", bookService.findById(bookId));
            return "loans/form";
        }
        try {
            loanService.loanBook(bookId, loan);
            redirectAttributes.addFlashAttribute("successMessage", "貸出を登録しました。");
        } catch (RuntimeException e) {   // ← IllegalStateExceptionから変更
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/books";
    }

    // ===========================
    // 返却処理
    // ===========================
    @PostMapping("/{loanId}/return")
    public String returnBook(
        @PathVariable Long loanId,
        RedirectAttributes redirectAttributes) {
    try {
        loanService.returnBook(loanId);
        redirectAttributes.addFlashAttribute("successMessage", "返却が完了しました。");
    } catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/loans";
    }
}
