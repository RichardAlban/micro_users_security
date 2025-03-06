package com.espe.loanservice.controller;

import com.espe.loanservice.model.BankAccount;
import com.espe.loanservice.model.Loan;
import com.espe.loanservice.model.LoanPayment;
import com.espe.loanservice.service.LoanService;
import com.espe.loanservice.service.LoanPaymentService;
import com.espe.loanservice.dto.PaymentRequestDTO;
import com.espe.loanservice.dto.PaymentResponseDTO;
import com.espe.loanservice.model.Transaction;
import com.espe.loanservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.espe.loanservice.service.BankAccountService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.espe.loanservice.model.User;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanPaymentService loanPaymentService;
    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;   

    public LoanController(LoanService loanService, LoanPaymentService loanPaymentService,
            TransactionService transactionService, BankAccountService bankAccountService) {
        this.loanService = loanService;
        this.loanPaymentService = loanPaymentService;
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String id) {
        return loanService.getLoanById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String id, @RequestBody Loan loanDetails) {
        return ResponseEntity.ok(loanService.updateLoan(id, loanDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable String id) {
        loanService.deleteLoanById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countLoansByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(loanService.countLoansByUserId(userId));
    }

    @GetMapping("/next-payment-date/{userId}")
    public ResponseEntity<Date> getNextPaymentDate(@PathVariable String userId) {
        Optional<Date> nextPaymentDate = loanService.getNextPaymentDate(userId);
        return nextPaymentDate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }

    @GetMapping("/{loanId}/next-payment")
    public String getNextPayment(@PathVariable String loanId) {
        Optional<Loan> loan = loanService.findById(loanId);
        if (loan.isEmpty()) {
            return "Préstamo no encontrado.";
        }

        Loan l = loan.get();
        return String.format("Siguiente pago: $%.2f, Fecha: %s, Meses restantes: %d",
                l.getMonthlyPayment(), l.getNextPaymentDate(), l.getRemainingMonths());
    }

    @PostMapping("/{loanId}/pay")
    public ResponseEntity<PaymentResponseDTO> makePayment(@PathVariable String loanId,
            @RequestBody PaymentRequestDTO paymentRequest) {
        Optional<Loan> loan = loanService.findById(loanId);
        if (loan.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new PaymentResponseDTO("Préstamo no encontrado.", 0, 0));
        }

        Loan l = loan.get();
        double amount = paymentRequest.getAmount();
        l.makePayment(amount);
        loanService.save(l);

        User user = l.getUser();
        Optional<BankAccount> bankAccountOpt = bankAccountService.getBankAccountByUserId(user.getId());
        if (bankAccountOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new PaymentResponseDTO("Cuenta bancaria no encontrada.", 0, 0));
        }
        BankAccount bankAccount = bankAccountOpt.get();

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionDate(new Date());
        transaction.setDescription("Pago de préstamo");
        transaction.setBankAccount(bankAccount);
        transactionService.saveTransaction(transaction);

        LoanPayment loanPayment = new LoanPayment();
        loanPayment.setAmount(amount);
        loanPayment.setPaymentDate(new Date());
        loanPayment.setLoan(l);
        loanPaymentService.saveLoanPayment(loanPayment);

        bankAccountService.deductBalance(bankAccount, amount);

        PaymentResponseDTO response = new PaymentResponseDTO(
                "Pago recibido.",
                l.getRemainingBalance(),
                l.getMonthlyPayment());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanService.saveLoan(loan);
        return ResponseEntity.ok(savedLoan);
    }
}
