package com.espe.loanservice.repository;

import com.espe.loanservice.model.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, String> {
}
