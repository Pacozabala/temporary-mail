package com.pacozabala.temporary_mail.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pacozabala.temporary_mail.dto.EmailResponse;
import com.pacozabala.temporary_mail.exception.EmailNotFoundException;
import com.pacozabala.temporary_mail.exception.MailboxNotFoundException;
import com.pacozabala.temporary_mail.model.Email;
import com.pacozabala.temporary_mail.model.Mailbox;
import com.pacozabala.temporary_mail.repository.EmailRepository;
import com.pacozabala.temporary_mail.repository.MailboxRepository;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final MailboxRepository mailboxRepository;

    public EmailService(MailboxRepository mailboxRepository, EmailRepository emailRepository) {
        this.mailboxRepository = mailboxRepository;
        this.emailRepository = emailRepository;
    }

    public Page<EmailResponse> getInbox(Long id, Pageable pageable, Boolean read) {

        Mailbox mailbox = mailboxRepository.findById(id).orElseThrow(()-> new MailboxNotFoundException(id));
        Long mailboxId = mailbox.getId();
        
        Page<Email> emails;

        if (read == null) {
            emails = emailRepository.findByMailboxId(mailboxId, pageable);
        } else {
            emails = emailRepository.findByMailboxIdAndIsRead(mailboxId, read, pageable);
        }
        return emails.map(email -> toEmailResponse(email));
    }

    public EmailResponse getEmail(Long id, Long emailId) {

        Email email = emailRepository.findById(emailId).orElseThrow(() -> new EmailNotFoundException(emailId));

        if (email.getMailbox().getId().equals(id)) {
            return toEmailResponse(email);
        }
        throw new EmailNotFoundException(emailId);
    }

    public EmailResponse markAsRead(Long id, Long emailId) {

        Email foundEmail = emailRepository.findById(emailId).orElseThrow(() -> new EmailNotFoundException(emailId));
        if (foundEmail.getMailbox().getId().equals(id)) {
            foundEmail.setIsRead(true);
            Email savedEmail = emailRepository.save(foundEmail);

            return toEmailResponse(savedEmail);
        }
        throw new EmailNotFoundException(emailId);
    }

    public void deleteEmail(Long id, Long emailId) {
        Email foundEmail = emailRepository.findById(emailId).orElseThrow(() -> new EmailNotFoundException(emailId));
        if (foundEmail.getMailbox().getId().equals(id)) {
           emailRepository.delete(foundEmail);
        }
        throw new EmailNotFoundException(emailId);
    }

    private EmailResponse toEmailResponse(Email email) {
        EmailResponse response = new EmailResponse(
            email.getId(), 
            email.getSender(), 
            email.getRecipient(),
            email.getSubject(), 
            email.getBody(), 
            email.getReceivedAt(),
            email.isRead()
        );

        return response;
    }
    
}
