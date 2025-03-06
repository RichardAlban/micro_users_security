package com.espe.loanservice.controller;

import com.espe.loanservice.model.BankAccount;
import com.espe.loanservice.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import com.espe.loanservice.dto.BankAccountResponseDTO;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/bankaccounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankAccountById(@PathVariable String id) {
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        return ResponseEntity.ok(bankAccountService.createBankAccount(bankAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable String id) {
        bankAccountService.deleteBankAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BankAccount> getBankAccountByUserId(@PathVariable String userId) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountByUserId(userId);
        return bankAccount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<BankAccountResponseDTO> getBankAccountDetailsByUserId(@PathVariable String userId) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountByUserId(userId);
        return bankAccount.map(account -> ResponseEntity.ok(new BankAccountResponseDTO(account.getAccountNumber(), account.getBalance(), account.getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
