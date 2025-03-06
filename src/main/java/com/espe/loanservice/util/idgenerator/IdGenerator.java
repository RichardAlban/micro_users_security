package com.espe.loanservice.util.idgenerator;

import java.util.Random;

public class IdGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ID_LETTERS_COUNT = 3;
    private static final int ID_NUMBERS_COUNT = 7;
    private static final int ACCOUNT_NUMBERS_COUNT = 10;
    private final Random random = new Random();

    public String generateId() {
        StringBuilder id = new StringBuilder(ID_LETTERS_COUNT + ID_NUMBERS_COUNT);
        for (int i = 0; i < ID_LETTERS_COUNT; i++) {
            id.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        for (int i = 0; i < ID_NUMBERS_COUNT; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public String generateBankAccount() {
        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBERS_COUNT);
        for (int i = 0; i < ACCOUNT_NUMBERS_COUNT; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}

