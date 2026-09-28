package com.pacozabala.temporary_mail.service;

import com.pacozabala.temporary_mail.repository.EmailRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pacozabala.temporary_mail.dto.EmailResponse;
import com.pacozabala.temporary_mail.dto.MailboxResponse;
import com.pacozabala.temporary_mail.exception.EmailNotFoundException;
import com.pacozabala.temporary_mail.exception.MailboxNotFoundException;
import com.pacozabala.temporary_mail.model.Email;
import com.pacozabala.temporary_mail.model.Mailbox;
import com.pacozabala.temporary_mail.repository.MailboxRepository;

@Service 
public class MailboxService {
    private final EmailRepository emailRepository;
    private final MailboxRepository mailboxRepository;

    public MailboxService(MailboxRepository mailboxRepository, EmailRepository emailRepository) {
        this.mailboxRepository = mailboxRepository;
        this.emailRepository = emailRepository;
    }

    public MailboxResponse createMailbox() {
        String address = generateAddress();
        LocalDateTime now = LocalDateTime.now();

        while (mailboxRepository.existsByAddress(address)) {
            address = generateAddress();
        }

        Mailbox mailbox = new Mailbox(
            address, 
            now,
            now.plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        return toMailboxResponse(savedMailbox);        
    }

    public MailboxResponse getMailbox(Long id) {
        Mailbox foundMailbox = mailboxRepository.findById(id).orElseThrow(()-> new MailboxNotFoundException(id));

        return toMailboxResponse(foundMailbox);
    }

    public void deleteMailbox(Long id) {
        Mailbox mailbox = mailboxRepository.findById(id).orElseThrow(()-> new MailboxNotFoundException(id));

        mailboxRepository.delete(mailbox);
    }

    public List<EmailResponse> getInbox(Long id) {
        List<EmailResponse> responses = new ArrayList<>();

        Mailbox mailbox = mailboxRepository.findById(id).orElseThrow(()-> new MailboxNotFoundException(id));
        Long mailboxId = mailbox.getId();
        for (Email email:  emailRepository.findByMailboxId(mailboxId)) {
            responses.add(toEmailResponse(email));
        }

        return responses;
    }

    public EmailResponse getEmail(Long id, Long emailId) {

        Email email = emailRepository.findById(emailId).orElseThrow(() -> new EmailNotFoundException(emailId));

        if (email.getMailbox().getId().equals(id)) {
            return toEmailResponse(email);
        }
        throw new EmailNotFoundException(emailId);
    }

    private String generateAddress() {
        String uuid = UUID.randomUUID().toString();
        String randomString = uuid.replace("-", "").substring(0, 10);

        return randomString + "@temporarymail.com";
    }
    
    private MailboxResponse toMailboxResponse(Mailbox mailbox) {
        MailboxResponse response = new MailboxResponse(
            mailbox.getId(), 
            mailbox.getAddress(), 
            mailbox.getExpiresAt()
        );

        return response;
    }

    private EmailResponse toEmailResponse(Email email) {
        EmailResponse response = new EmailResponse(
            email.getId(), 
            email.getSender(), 
            email.getRecipient(),
            email.getSubject(), 
            email.getBody(), 
            email.getReceivedAt()
        );

        return response;
    }
}
