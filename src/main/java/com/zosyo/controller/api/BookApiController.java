package com.zosyo.controller.api;

import com.zosyo.dto.BookRequest;
import com.zosyo.dto.BookResponse;
import com.zosyo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    // 書籍一覧取得
    @GetMapping
    public List<BookResponse> list() {
        return bookService.findAll().stream()
                .map(BookResponse::from)
                .toList();
    }

    // 書籍情報取得
    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Long id) {
        return BookResponse.from(bookService.findById(id));
    }

    // 書籍登録
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        return BookResponse.from(bookService.save(request.toEntity()));
    }

    // 書籍更新
    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return BookResponse.from(bookService.update(id, request.toEntity()));
    }

    // 書籍削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "削除しました。"));
    }
}