package com.pacozabala.temporary_mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacozabala.temporary_mail.model.Mailbox;
 
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {

    boolean existsByAddress(String address);
    
}
