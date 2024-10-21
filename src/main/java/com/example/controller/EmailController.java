package com.example.controller;

import com.example.request.EmailEngineAccountTokenDTO;
import com.example.request.EmailEngineAuthenticationFormDTO;
import com.example.request.GetAttachmentDTO;
import com.example.request.GetMessageDTO;
import com.example.request.SendMailDTO;
import com.example.request.SendMailRequest;
import com.example.request.VerifyConnectionRequest;
import com.example.response.EmailSuccessResponse;
import com.example.response.InboxResponse;
import com.example.service.EmailService;
//import org.openapitools.client.ApiException;
//import org.openapitools.client.model.SubmitMessageResponse;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.AccountResponse;
import org.openapitools.client.model.CreateTokenResponse;
import org.openapitools.client.model.DeleteRequestResponse;
import org.openapitools.client.model.DeleteTokenRequestResponse;
import org.openapitools.client.model.MessageDetails;
import org.openapitools.client.model.RequestAuthFormResponse;
import org.openapitools.client.model.SubmitMessageResponse;
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

    @PostMapping("/verifyAccount")
    public boolean verifyAccount(@RequestBody VerifyConnectionRequest request) throws ApiException {
        return emailService.verifyConnection(request);
    }

    @GetMapping("/account/{account}")
    public AccountResponse getAccount(@PathVariable String account) throws ApiException {
        return emailService.getAccount(account);
    }

    @DeleteMapping("/account/{account}")
    public DeleteRequestResponse deleteAccount(@PathVariable String account) throws ApiException{
        return emailService.deleteAccount(account);
    }

    @PostMapping("/token")
    public CreateTokenResponse generateToken(@RequestBody EmailEngineAccountTokenDTO emailEngineAccountTokenDTO) throws ApiException{
        return emailService.generateToken(emailEngineAccountTokenDTO);
    }

    @DeleteMapping("/token/{token}")
    public DeleteTokenRequestResponse deleteToken(@PathVariable String token) throws ApiException {
        return emailService.deleteToken(token);
    }

    @PostMapping("getAuthenticationLink")
    public RequestAuthFormResponse getAuthenticationLink(@RequestBody EmailEngineAuthenticationFormDTO emailEngineAuthenticationFormDTO) throws ApiException{
        return emailService.getAuthenticationLink((emailEngineAuthenticationFormDTO));
    }

    @GetMapping("/getMessage")
    public MessageDetails getMessages(@RequestBody GetMessageDTO getMessageDTO) throws ApiException {
        return emailService.getMessage(getMessageDTO);
    }

    @GetMapping("/getAttachment")
    public String getAttachment(@RequestBody GetAttachmentDTO getAttachmentDTO) throws ApiException {
        return emailService.getAttachment(getAttachmentDTO);
    }

    @PostMapping("/sendMailSdk")
    public SubmitMessageResponse sendMailSdk(@RequestBody SendMailDTO sendMailDTO) throws ApiException {
        return emailService.sendMailSdk(sendMailDTO);
    }

    @PostMapping("/sendmail")
    public Mono<EmailSuccessResponse> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        return emailService.sendMail(sendMailRequest);
    }

}

