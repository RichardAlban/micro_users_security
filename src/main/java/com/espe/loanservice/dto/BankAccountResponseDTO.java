package com.espe.loanservice.dto;

public class BankAccountResponseDTO {
    private String accountNumber;
    private double balance;
    private String id;

    public BankAccountResponseDTO(String accountNumber, double balance, String id) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
