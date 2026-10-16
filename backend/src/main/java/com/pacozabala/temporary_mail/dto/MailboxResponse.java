package com.pacozabala.temporary_mail.dto;

import java.time.LocalDateTime;

public record MailboxResponse(
    Long id,
    String address,
    LocalDateTime expiresAt
) {}