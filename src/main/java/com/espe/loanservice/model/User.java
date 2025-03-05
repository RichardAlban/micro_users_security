package com.espe.loanservice.model;

import com.espe.loanservice.util.idgenerator.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String idNumber;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String answerQuestion;

    private String address;

    @NotNull
    private double income;

    @NotNull
    private int creditScore;

    @NotNull
    private String employmentStatus;

    private Boolean isSuitable;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "max_loan_amount")
    private double maxLoanAmount;

    @PrePersist
    private void generateId() {
        IdGenerator idGenerator = new IdGenerator();
        this.id = idGenerator.generateId();
    }

    @Enumerated(EnumType.STRING)
    private Role role;
}
