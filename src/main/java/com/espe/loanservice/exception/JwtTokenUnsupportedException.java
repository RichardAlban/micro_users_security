package com.espe.loanservice.exception;

public class JwtTokenUnsupportedException extends JwtTokenException {
    public JwtTokenUnsupportedException(String message) {
        super(message);
    }
}