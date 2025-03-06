package com.espe.loanservice.repository;

import com.espe.loanservice.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {
    long countByUserId(String userId);
    List<Loan> findByUserId(String userId);
}
