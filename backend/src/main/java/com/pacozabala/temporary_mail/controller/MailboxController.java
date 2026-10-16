package com.pacozabala.temporary_mail.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacozabala.temporary_mail.dto.EmailResponse;
import com.pacozabala.temporary_mail.dto.MailboxResponse;
import com.pacozabala.temporary_mail.service.EmailService;
import com.pacozabala.temporary_mail.service.MailboxService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/mailboxes")
public class MailboxController {
    private final MailboxService mailboxService;
    private final EmailService emailService;

    public MailboxController(MailboxService mailboxService, EmailService emailService) {
        this.mailboxService = mailboxService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<MailboxResponse> createMailbox() {
        MailboxResponse response = mailboxService.createMailbox();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailboxResponse> getMailbox(@PathVariable Long id) {
        MailboxResponse response = mailboxService.getMailbox(id);

        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMailbox(@PathVariable Long id) {
        mailboxService.deleteMailbox(id);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/emails")
    public Page<EmailResponse> getInbox(
        @PathVariable Long id, 
        @PageableDefault(size=20, sort="receivedAt", direction=Sort.Direction.DESC) Pageable pageable
    ) {
        return emailService.getInbox(id, pageable);
    }

    @GetMapping("/{id}/emails/{emailId}")
    public ResponseEntity<EmailResponse> getEmail(@PathVariable Long id, @PathVariable Long emailId) {
        EmailResponse email = emailService.getEmail(id, emailId);

        return ResponseEntity.ok(email);
    }
    
    
}
