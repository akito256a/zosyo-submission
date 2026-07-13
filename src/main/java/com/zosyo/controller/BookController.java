package com.zosyo.controller;

import com.zosyo.entity.Book;
import com.zosyo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // コンストラクタインジェクション(DI)
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ===========================
    // 一覧表示・検索
    // ===========================
    @GetMapping
    public String index(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            Model model) {

        model.addAttribute("books", bookService.searchBooks(title, category));
        model.addAttribute("categories", bookService.findAllCategories());
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchCategory", category);
        return "books/index";
    }

    // ===========================
    // 登録画面表示
    // ===========================
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookService.findAllCategories());
        return "books/form";
    }

    // ===========================
    // 登録処理
    // ===========================
    @PostMapping
    public String create(
            @Valid @ModelAttribute Book book,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", bookService.findAllCategories());
            return "books/form";
        }
        bookService.save(book);
        redirectAttributes.addFlashAttribute("successMessage", "蔵書を登録しました。");
        return "redirect:/books";
    }

    // ===========================
    // 編集画面表示
    // ===========================
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", bookService.findAllCategories());
        return "books/form";
    }

    // ===========================
    // 更新処理
    // ===========================
    @PostMapping("/{id}/update")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute Book book,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", bookService.findAllCategories());
            return "books/form";
        }
        bookService.update(id, book);
        redirectAttributes.addFlashAttribute("successMessage", "蔵書情報を更新しました。");
        return "redirect:/books";
    }

    // ===========================
    // 削除処理
    // ===========================
    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        bookService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "蔵書を削除しました。");
        return "redirect:/books";
    }
}