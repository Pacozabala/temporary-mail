package com.pacozabala.temporary_mail.dto;

import java.time.LocalDateTime;

public record EmailResponse(
    Long id,
    String sender,
    String recipient,
    String subject,
    String body,
    LocalDateTime receivedAt,
    boolean isRead
) {}
