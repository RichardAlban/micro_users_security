package com.espe.loanservice.service;

import com.espe.loanservice.model.Loan;
import com.espe.loanservice.repository.LoanRepository;
import com.espe.loanservice.repository.UserRepository;
import com.espe.loanservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(String id) {
        return loanRepository.findById(id);
    }

    public Loan saveLoan(Loan loan) {
        Optional<User> userOpt = userRepository.findById(loan.getUser().getId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        if (!Boolean.TRUE.equals(user.getIsSuitable())) {
            throw new RuntimeException("User is not suitable for a loan");
        }

        Loan savedLoan = loanRepository.save(loan);
        user.setIsSuitable(false);
        userRepository.save(user);

        return savedLoan;
    }

    public Loan updateLoan(String id, Loan loanDetails) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setAmount(loanDetails.getAmount());
        loan.setInterestRate(loanDetails.getInterestRate());
        loan.setStartDate(loanDetails.getStartDate());
        loan.setEndDate(loanDetails.getEndDate());
        loan.setApproved(loanDetails.isApproved());

        return loanRepository.save(loan);
    }

    public void deleteLoanById(String id) {
        loanRepository.deleteById(id);
    }

    public long countLoansByUserId(String userId) {
        return loanRepository.countByUserId(userId);
    }

    public Optional<Date> getNextPaymentDate(String userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);

        return loans.stream()
                .filter(Loan::isApproved)
                .map(this::calculateNextPaymentDate)
                .min(Date::compareTo);
    }

    private Date calculateNextPaymentDate(Loan loan) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getStartDate());
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public List<Loan> getLoansByUserId(String userId) {
        return loanRepository.findByUserId(userId);
    }


    public Optional<Loan> findById(String id) {
        return loanRepository.findById(id);
    }

    public void save(Loan loan) {
        loanRepository.save(loan);
    }
}
