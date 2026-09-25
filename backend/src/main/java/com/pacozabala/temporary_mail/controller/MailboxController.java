package com.pacozabala.temporary_mail.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacozabala.temporary_mail.dto.MailboxResponse;
import com.pacozabala.temporary_mail.service.MailboxService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

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
        
        return ResponseEntity.ok(response);
    }
    
}
