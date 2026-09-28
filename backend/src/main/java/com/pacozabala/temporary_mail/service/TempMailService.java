package com.pacozabala.temporary_mail.service;

import org.springframework.stereotype.Service;

@Service 
public class TempMailService {
    private String testString;

    public TempMailService() {
        this.testString = "Service dependency injection is working!";
    }

    public String test() {
        return testString;
    }
}
