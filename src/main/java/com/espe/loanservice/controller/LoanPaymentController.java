package com.espe.loanservice.controller;

import com.espe.loanservice.model.LoanPayment;
import com.espe.loanservice.service.LoanPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/loan-payments")
@RequiredArgsConstructor
public class LoanPaymentController {

    private final LoanPaymentService loanPaymentService;

    @GetMapping
    public ResponseEntity<List<LoanPayment>> getAllLoanPayments() {
        return ResponseEntity.ok(loanPaymentService.getAllLoanPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LoanPayment>> getLoanPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(loanPaymentService.getLoanPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<LoanPayment> createLoanPayment(@RequestBody LoanPayment loanPayment) {
        return ResponseEntity.ok(loanPaymentService.saveLoanPayment(loanPayment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanPayment> updateLoanPayment(@PathVariable String id, @RequestBody LoanPayment loanPaymentDetails) {
        return ResponseEntity.ok(loanPaymentService.updateLoanPayment(id, loanPaymentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanPayment(@PathVariable String id) {
        loanPaymentService.deleteLoanPaymentById(id);
        return ResponseEntity.noContent().build();
    }
}
