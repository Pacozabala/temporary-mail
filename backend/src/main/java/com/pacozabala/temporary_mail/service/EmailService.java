package com.pacozabala.temporary_mail.service;

import java.util.ArrayList;
import java.util.List;

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

    private EmailResponse toEmailResponse(Email email) {
        EmailResponse response = new EmailResponse(
            email.getId(), 
            email.getSender(), 
            email.getRecipient(),
            email.getSubject(), 
            email.getBody(), 
            email.getReceivedAt(),
            email.getIsRead()
        );

        return response;
    }
    
}
