package com.nfaustino.splitmoney.shared.base.exceptions;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
