package com.nfaustino.splitmoney.shared.exceptions;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
