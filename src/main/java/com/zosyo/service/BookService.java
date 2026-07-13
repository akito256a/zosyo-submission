package com.zosyo.service;

import com.zosyo.entity.Book;
import com.zosyo.exception.ResourceNotFoundException;
import com.zosyo.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    // コンストラクタインジェクション(DI)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ===========================
    // 全件取得します
    // ===========================
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // ===========================
    // ID指定で1件取得します
    // ===========================
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("蔵書が見つかりません。ID: " + id));
    }

    // ===========================
    // タイトル・カテゴリで検索
    // ===========================
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String title, String category) {
        // 空文字はnullに変換して検索
        String searchTitle = (title != null && !title.isBlank()) ? title : null;
        String searchCategory = (category != null && !category.isBlank()) ? category : null;
        return bookRepository.searchBooks(searchTitle, searchCategory);
    }

    // ===========================
    // 登録
    // ===========================
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // ===========================
    // 更新
    // ===========================
    public Book update(Long id, Book updatedBook) {
        Book existingBook = findById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setQuantity(updatedBook.getQuantity());
        existingBook.setStock(updatedBook.getStock());
        existingBook.setNote(updatedBook.getNote());
        return bookRepository.save(existingBook);
    }

    // ===========================
    // 削除
    // ===========================
    public void deleteById(Long id) {
        // 存在確認してから削除
        findById(id);
        bookRepository.deleteById(id);
    }

    // ===========================
    // カテゴリ一覧取得（検索プルダウン用）
    // ===========================
    @Transactional(readOnly = true)
    public List<String> findAllCategories() {
        return bookRepository.findAll()
                .stream()
                .map(Book::getCategory)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .sorted()
                .toList();
    }
}