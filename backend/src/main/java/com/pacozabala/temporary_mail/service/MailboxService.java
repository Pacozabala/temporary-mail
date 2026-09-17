package com.pacozabala.temporary_mail.service;

import org.springframework.stereotype.Service;

import com.pacozabala.temporary_mail.repository.MailboxRepository;

@Service 
public class MailboxService {
    private final MailboxRepository mailboxRepository;

    public MailboxService(MailboxRepository mailboxRepository) {
        this.mailboxRepository = mailboxRepository;
    }

    
}
