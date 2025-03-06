package com.espe.loanservice.service;

import com.espe.loanservice.model.Transaction;
import com.espe.loanservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(String id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setTransactionDate(transactionDetails.getTransactionDate());
        transaction.setBankAccount(transactionDetails.getBankAccount());
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionById(String id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionsByBankAccountId(String bankAccountId) {
        return transactionRepository.findByBankAccountId(bankAccountId);
    }
}
