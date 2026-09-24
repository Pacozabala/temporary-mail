package com.pacozabala.temporary_mail.dto;

import java.time.LocalDateTime;

public class MailboxResponse {
    
    private Long id;
    private String address;
    private LocalDateTime expiresAt;
    
    public MailboxResponse(Long id, String address, LocalDateTime expiresAt) {
        this.id = id;
        this.address = address;
        this.expiresAt = expiresAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    
}
