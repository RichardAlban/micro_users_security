package com.espe.loanservice.service;

import com.espe.loanservice.model.BankAccount;
import com.espe.loanservice.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public void deleteBankAccountById(String id) {
        bankAccountRepository.deleteById(id);
    }

    public Optional<BankAccount> getBankAccountByUserId(String userId) {
        return bankAccountRepository.findByUserId(userId);
    }

    public void deductBalance(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() >= amount) {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        } else {
            throw new RuntimeException("Insufficient balance");
        }
    }
}
