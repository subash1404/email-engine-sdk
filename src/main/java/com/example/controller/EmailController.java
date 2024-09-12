package com.example.controller;

import com.example.request.SendMailRequest;
import com.example.response.EmailSuccessResponse;
import com.example.response.InboxResponse;
import com.example.service.EmailService;
//import org.openapitools.client.ApiException;
//import org.openapitools.client.model.SubmitMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/inbox")
    public Mono<InboxResponse> getInbox() {
        return emailService.fetchInbox();
    }

    @PostMapping("/webhooks")
    public String getWebhooks(@RequestBody String payload) {
        System.out.println(payload);
        return emailService.getWebHooks(payload);
    }

    @PostMapping("/sendmail")
    public Mono<EmailSuccessResponse> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        return emailService.sendMail(sendMailRequest);
    }

//    @PostMapping("/sendMailSdk")
//    public SubmitMessageResponse sendMailSdk() throws ApiException {
//        return emailService.sendMailSdk();
//    }

}

