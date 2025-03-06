package com.espe.loanservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

import com.espe.loanservice.util.idgenerator.IdGenerator;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    private String id;

    private double amount;

    private Date transactionDate;

    @ManyToOne
    private BankAccount bankAccount;

    private String description;

    @PrePersist
    private void generateId() {
        IdGenerator idGenerator = new IdGenerator();
        this.id = idGenerator.generateId();
    }
}
