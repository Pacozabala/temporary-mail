package com.pacozabala.temporary_mail.controller;

import org.springframework.web.bind.annotation.*;

import com.pacozabala.temporary_mail.service.TempMailService;


@RestController
@RequestMapping("/api/temp-mail")
public class TempMailController {
    
    private final TempMailService tempMailService;

    public TempMailController(TempMailService tempMailService) {
        this.tempMailService = tempMailService;
    }

    @GetMapping("/test")
    public String test() {
        return tempMailService.test();
    }
    

}
