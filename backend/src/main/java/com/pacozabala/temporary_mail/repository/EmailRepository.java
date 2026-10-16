package com.pacozabala.temporary_mail.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pacozabala.temporary_mail.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{
    Page<Email> findByMailboxId(Long mailboxId, Pageable pageable);

    Page<Email> findByMailboxIdAndIsRead(Long mailboxId, boolean isRead, Pageable pageable);

    List<Email> findByMailboxIdOrderByReceivedAtDesc(Long mailboxId);
}
