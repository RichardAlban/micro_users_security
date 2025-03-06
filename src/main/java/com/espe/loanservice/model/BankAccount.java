package com.espe.loanservice.model;

import com.espe.loanservice.util.idgenerator.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bank_accounts")
@Data
public class BankAccount {
    @Id
    private String id;

    private String accountNumber;

    private double balance;

    @ManyToOne
    private User user;

    @PrePersist
    private void generateId() {
        IdGenerator idGenerator = new IdGenerator();
        this.id = idGenerator.generateId();
        this.accountNumber = idGenerator.generateBankAccount();
    }
}
