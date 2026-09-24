package com.pacozabala.temporary_mail.service;

import java.time.LocalDateTime;
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
        String address = generateAddress();

        while (mailboxRepository.existsByAddress(address)) {
            address = generateAddress();
        }

        Mailbox mailbox = new Mailbox(
            address, 
            LocalDateTime.now(), 
            LocalDateTime.now().plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        return toResponse(savedMailbox);        
    }

    public MailboxResponse getMailbox(Long id) {
        Mailbox foundMailbox = mailboxRepository.findById(id).get();

        return toResponse(foundMailbox);
    }

    public void deleteMailbox(Long id) {
        mailboxRepository.deleteById(id);
    }

    private String generateAddress() {
        String uuid = UUID.randomUUID().toString();
        String randomString = uuid.replace("-", "").substring(0, 10);

        return randomString + "@temporarymail.com";
    }
    
    private MailboxResponse toResponse(Mailbox mailbox) {
        MailboxResponse response = new MailboxResponse(
            mailbox.getId(), 
            mailbox.getAddress(), 
            mailbox.getExpiresAt()
        );

        return response;
    }
}
