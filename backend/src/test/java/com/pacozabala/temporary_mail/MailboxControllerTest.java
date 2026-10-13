package com.pacozabala.temporary_mail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.pacozabala.temporary_mail.model.Email;
import com.pacozabala.temporary_mail.model.Mailbox;
import com.pacozabala.temporary_mail.repository.EmailRepository;
import com.pacozabala.temporary_mail.repository.MailboxRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest 
@AutoConfigureMockMvc 
public class MailboxControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    private ObjectMapper objectMapper;

    @Autowired 
    private MailboxRepository mailboxRepository;

    @Autowired 
    private EmailRepository emailRepository;

    @BeforeEach 
    void setUp() {
        emailRepository.deleteAll();
        mailboxRepository.deleteAll();
    }

    @Test 
    void createMailbox_shouldReturnCreatedMailbox() throws Exception {

        String response = mockMvc.perform(post("/mailboxes"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.address").exists())
            .andExpect(jsonPath("$.expiresAt").exists())
            .andReturn()
            .getResponse()
            .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        Long mailboxId = json.get("id").asLong();
        Mailbox mailbox = mailboxRepository.findById(mailboxId).orElseThrow();

        org.junit.jupiter.api.Assertions.assertEquals(
            json.get("address").asString(),
            mailbox.getAddress()
        );
    }

    @Test 
    void getMailbox_shouldReturnMailbox() throws Exception {
        
        Mailbox mailbox = new Mailbox(
            "test123@temporarymail.com",
            LocalDateTime.now(), 
            LocalDateTime.now().plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        mockMvc.perform(get("/mailboxes/{id}", savedMailbox.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(savedMailbox.getId()))
            .andExpect(jsonPath("$.address").value("test123@temporarymail.com"))
            .andExpect(jsonPath("$.expiresAt").exists()
        );
    }

   @Test
    void deleteMailbox_shouldDeleteMailbox() throws Exception {

        Mailbox mailbox = new Mailbox(
                "delete123@temporarymail.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        mockMvc.perform(delete("/mailboxes/{id}", savedMailbox.getId()))
                .andExpect(status().isNoContent());

        org.junit.jupiter.api.Assertions.assertFalse(
                mailboxRepository.existsById(savedMailbox.getId())
        );
    }

    @Test
    void getInbox_shouldReturnEmailsForMailbox() throws Exception {

        Mailbox mailbox = new Mailbox(
                "inbox123@temporarymail.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        Email email = new Email(
                savedMailbox,
                "sender@example.com",
                savedMailbox.getAddress(),
                "Test subject",
                "Test body",
                LocalDateTime.now(),
                true
        );

        emailRepository.save(email);

        mockMvc.perform(get("/mailboxes/{id}/emails", savedMailbox.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].sender")
                        .value("sender@example.com"))
                .andExpect(jsonPath("$[0].recipient")
                        .value(savedMailbox.getAddress()))
                .andExpect(jsonPath("$[0].subject")
                        .value("Test subject"))
                .andExpect(jsonPath("$[0].body")
                        .value("Test body"));
    }

    @Test
    void getEmail_shouldReturnEmail() throws Exception {

        Mailbox mailbox = new Mailbox(
                "email123@temporarymail.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
        );

        Mailbox savedMailbox = mailboxRepository.save(mailbox);

        Email email = new Email(
                savedMailbox,
                "sender@example.com",
                savedMailbox.getAddress(),
                "Individual email",
                "Email body",
                LocalDateTime.now(),
                true
        );

        Email savedEmail = emailRepository.save(email);

        mockMvc.perform(get(
                "/mailboxes/{id}/emails/{emailId}",
                savedMailbox.getId(),
                savedEmail.getId()
        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEmail.getId()))
                .andExpect(jsonPath("$.sender")
                        .value("sender@example.com"))
                .andExpect(jsonPath("$.subject")
                        .value("Individual email"))
                .andExpect(jsonPath("$.body")
                        .value("Email body"));
    }

}
