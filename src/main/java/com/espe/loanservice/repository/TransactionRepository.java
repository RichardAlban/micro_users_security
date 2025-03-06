package com.espe.loanservice.repository;

import com.espe.loanservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByBankAccountId(String bankAccountId);
}
