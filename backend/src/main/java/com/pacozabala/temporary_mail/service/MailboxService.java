package com.pacozabala.temporary_mail.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pacozabala.temporary_mail.dto.MailboxResponse;
import com.pacozabala.temporary_mail.model.Mailbox;
import com.pacozabala.temporary_mail.repository.MailboxRepository;

@Service 
public class MailboxService {
    private final MailboxRepository mailboxRepository;

    public MailboxService(MailboxRepository mailboxRepository) {
        this.mailboxRepository = mailboxRepository;
    }

    public MailboxResponse createMailbox() {
        
    }

    public MailboxResponse getMailbox(Long id) {

    }

    public void deleteMailbox(Long id) {

    }

    private String generateAddress() {
        String uuid = UUID.randomUUID().toString();

        String randomString = uuid.replace("-", "").substring(0, 10);

        return randomString + "@temporarymail.com";
    }
    
}
