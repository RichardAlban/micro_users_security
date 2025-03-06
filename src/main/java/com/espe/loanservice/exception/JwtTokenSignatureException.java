package com.espe.loanservice.exception;

public class JwtTokenSignatureException extends JwtTokenException {
    public JwtTokenSignatureException(String message) {
        super(message);
    }
}