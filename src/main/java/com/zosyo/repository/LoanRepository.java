package com.zosyo.repository;

import com.zosyo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // 書籍IDに紐づく貸出一覧を貸出日時の降順で取得する
    List<Loan> findByBookIdOrderByLoanedAtDesc(Long bookId);

    // 全貸出を貸出日時の降順で取得する
    List<Loan> findAllByOrderByLoanedAtDesc();

    // 返却済みではない(貸出中)ものだけを貸出日時の降順で取得する → API /api/loans 用です
    List<Loan> findByReturnDateIsNullOrderByLoanedAtDesc();
}