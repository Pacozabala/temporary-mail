package com.pacozabala.temporary_mail.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(Long id) {
        super("Email with id " +id+ " not found.");
    }
}
