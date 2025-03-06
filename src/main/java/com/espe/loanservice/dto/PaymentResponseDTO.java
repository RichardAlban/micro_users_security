package com.espe.loanservice.dto;

public class PaymentResponseDTO {
    private String message;
    private double newBalance;
    private double nextPayment;

    public PaymentResponseDTO(String message, double newBalance, double nextPayment) {
        this.message = message;
        this.newBalance = newBalance;
        this.nextPayment = nextPayment;
    }

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public double getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(double nextPayment) {
        this.nextPayment = nextPayment;
    }
}