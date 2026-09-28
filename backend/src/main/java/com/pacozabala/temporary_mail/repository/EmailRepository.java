package com.pacozabala.temporary_mail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacozabala.temporary_mail.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{
    List<Email> findByMailboxId(Long mailboxId);
}
