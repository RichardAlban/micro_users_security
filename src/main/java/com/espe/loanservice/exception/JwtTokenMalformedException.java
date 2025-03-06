package com.espe.loanservice.exception;

public class JwtTokenMalformedException extends JwtTokenException {
    public JwtTokenMalformedException(String message) {
        super(message);
    }
}
