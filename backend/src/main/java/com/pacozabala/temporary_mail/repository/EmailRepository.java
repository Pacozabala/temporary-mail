package com.pacozabala.temporary_mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacozabala.temporary_mail.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{
    
}
