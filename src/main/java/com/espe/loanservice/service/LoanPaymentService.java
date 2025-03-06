package com.espe.loanservice.service;

import com.espe.loanservice.model.LoanPayment;
import com.espe.loanservice.repository.LoanPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanPaymentService {

    private final LoanPaymentRepository loanPaymentRepository;

    public List<LoanPayment> getAllLoanPayments() {
        return loanPaymentRepository.findAll();
    }

    public Optional<LoanPayment> getLoanPaymentById(String id) {
        return loanPaymentRepository.findById(id);
    }

    public LoanPayment saveLoanPayment(LoanPayment loanPayment) {
        return loanPaymentRepository.save(loanPayment);
    }

    public LoanPayment updateLoanPayment(String id, LoanPayment loanPaymentDetails) {
        LoanPayment loanPayment = loanPaymentRepository.findById(id).orElseThrow(() -> new RuntimeException("LoanPayment not found"));
        loanPayment.setAmount(loanPaymentDetails.getAmount());
        loanPayment.setPaymentDate(loanPaymentDetails.getPaymentDate());
        loanPayment.setLoan(loanPaymentDetails.getLoan());
        return loanPaymentRepository.save(loanPayment);
    }

    public void deleteLoanPaymentById(String id) {
        loanPaymentRepository.deleteById(id);
    }

    
}
