package com.espe.loanservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Calendar;
import com.espe.loanservice.util.idgenerator.IdGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id
    private String id;
    private double amount;
    private double interestRate;
    private int term;
    private Date startDate;
    private Date endDate;
    private boolean approved;
    private String method;
    private double monthlyPayment;
    private double remainingBalance;
    private int paymentsMade;
    private double totalToPay;

    @ManyToOne
    private User user;

    @PrePersist
    private void generateId() {
        IdGenerator idGenerator = new IdGenerator();
        this.id = idGenerator.generateId();
    }

    public void calculateLoan() {
        if (amount <= 0 || term <= 0 || interestRate <= 0) return;

        this.remainingBalance = amount;
        double monthlyRate = interestRate;
        this.paymentsMade = 0;

        if ("MF".equalsIgnoreCase(method)) {
            double factor = Math.pow(1 + monthlyRate, -term);
            this.monthlyPayment = (amount * monthlyRate) / (1 - factor);
        } else {
            this.monthlyPayment = (amount / term) + (remainingBalance * monthlyRate);
        }

        this.totalToPay = this.monthlyPayment * term;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, term);
        this.endDate = calendar.getTime();
    }

    public Date getNextPaymentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, paymentsMade);
        return calendar.getTime();
    }

    public int getRemainingMonths() {
        return term - paymentsMade;
    }

    public void makePayment(double amountPaid) {
        if (amountPaid <= 0 || remainingBalance <= 0) return;

        double interest = remainingBalance * interestRate;
        double principal = amountPaid - interest;

        remainingBalance -= principal;
        paymentsMade++;

        if ("MA".equalsIgnoreCase(method)) {
            this.monthlyPayment = (remainingBalance / (term - paymentsMade)) + (remainingBalance * interestRate);
        }

        this.totalToPay = remainingBalance + (remainingBalance * interestRate * getRemainingMonths());

        if (remainingBalance <= 0) {
            this.approved = false;
            this.user.setIsSuitable(true);
        }
    }
}