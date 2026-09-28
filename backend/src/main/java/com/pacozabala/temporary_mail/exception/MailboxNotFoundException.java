package com.pacozabala.temporary_mail.exception;

public class MailboxNotFoundException extends RuntimeException {
    public MailboxNotFoundException(Long id) {
        super("Mailbox with id " +id+ " not found.");
    }
}
