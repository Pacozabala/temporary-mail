package com.pacozabala.temporary_mail.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacozabala.temporary_mail.dto.EmailResponse;
import com.pacozabala.temporary_mail.dto.MailboxResponse;
import com.pacozabala.temporary_mail.service.MailboxService;

import java.util.List;

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

    public MailboxController(MailboxService mailboxService) {
        this.mailboxService = mailboxService;
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
    public List<EmailResponse> getInbox(@PathVariable Long id) {
        return mailboxService.getInbox(id);
    }

    @GetMapping("/{id}/emails/{emailId}")
    public ResponseEntity<EmailResponse> getEmail(@PathVariable Long id, @PathVariable Long emailId) {
        EmailResponse email = mailboxService.getEmail(id, emailId);

        return ResponseEntity.ok(email);
    }
    
    
}
