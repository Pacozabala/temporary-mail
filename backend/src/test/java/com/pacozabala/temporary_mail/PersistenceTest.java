package com.pacozabala.temporary_mail;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pacozabala.temporary_mail.model.Mailbox;
import com.pacozabala.temporary_mail.repository.MailboxRepository;

@SpringBootTest 
public class PersistenceTest {
    @Autowired 
    private MailboxRepository mailboxRepository;

    @Test
    void saveMailbox() {
        Mailbox mailbox = new Mailbox(
            "test@temporary_mail.com",
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1)
        );

        Mailbox saved = mailboxRepository.save(mailbox);

        System.out.println("Saved mailbox ID: " + saved.getId());

        assertNotNull(saved);
    }
}
