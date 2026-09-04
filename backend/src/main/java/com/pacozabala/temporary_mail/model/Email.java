package com.pacozabala.temporary_mail.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity 
public class Email {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mailboxId;
    private String sender;
    private String recipient;
    private String subject;
    private String body;
    private LocalDateTime receivedAt;
    
    public Email(Long id, Long mailboxId, String sender, String recipient, String subject, String body,
            LocalDateTime receivedAt) {
        this.id = id;
        this.mailboxId = mailboxId;
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMailboxId() {
        return mailboxId;
    }

    public void setMailboxId(Long mailboxId) {
        this.mailboxId = mailboxId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    
    
}
