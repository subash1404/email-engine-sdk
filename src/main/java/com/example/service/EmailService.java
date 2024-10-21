package com.example.service;

import com.example.request.EmailEngineAccountTokenDTO;
import com.example.request.EmailEngineAuthenticationFormDTO;
import com.example.request.GetAttachmentDTO;
import com.example.request.GetMessageDTO;
import com.example.request.SendMailDTO;
import com.example.request.SendMailRequest;
import com.example.request.VerifyConnectionRequest;
import com.example.response.EmailSuccessResponse;
import com.example.response.InboxResponse;
import com.example.response.Message;
import okhttp3.Call;
import org.hibernate.sql.Delete;
import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.AccessTokensApi;
import org.openapitools.client.api.AccountApi;
import org.openapitools.client.api.MessageApi;
import org.openapitools.client.api.SubmitApi;
import org.openapitools.client.model.AccountResponse;
import org.openapitools.client.model.Authentication;
import org.openapitools.client.model.CreateToken;
import org.openapitools.client.model.CreateTokenResponse;
import org.openapitools.client.model.DeleteRequestResponse;
import org.openapitools.client.model.DeleteTokenRequestResponse;
import org.openapitools.client.model.FromAddress;
import org.openapitools.client.model.ImapConfiguration;
import org.openapitools.client.model.MessageDetails;
import org.openapitools.client.model.MessageList;
import org.openapitools.client.model.RequestAuthForm;
import org.openapitools.client.model.RequestAuthFormResponse;
import org.openapitools.client.model.SmtpConfiguration;
import org.openapitools.client.model.SubmitMessage;
import org.openapitools.client.model.SubmitMessageResponse;
import org.openapitools.client.model.ToAddress;
import org.openapitools.client.model.TokenScope;
import org.openapitools.client.model.VerifyAccount;
import org.openapitools.client.model.VerifyAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class EmailService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private SubmitApi submitApi;
    private AccountApi accountApi;
    private AccessTokensApi accessTokensApi;
    public MessageApi messageApi;

    @PostConstruct
    public void init() {
        ApiClient apiClient = new ApiClient();
//        apiClient.selectHeaderContentType(new String[]{"*/*"});
        apiClient.setBasePath("http://localhost:3000");
        apiClient.selectHeaderContentType(new String[]{"application/json"});
//        apiClient.setApiKeyPrefix("Bearer ");
            apiClient.setApiKey("90a1699ba20b976043d9659edfbd553f4a0ea406e032c6dff882dcc491943ace");
//        apiClient.setAccessToken("Bearer fe0c3b98f532373ddd4 4394f60a93f38d611364c77e00f1113a91b18edf04778");
         submitApi = new SubmitApi(apiClient);
         accountApi = new AccountApi(apiClient);
         accessTokensApi = new AccessTokensApi(apiClient);
         messageApi = new MessageApi(apiClient);
    }

    public Mono<InboxResponse> fetchInbox() {
        WebClient webClient = webClientBuilder.build();
        return webClient.get()
                .uri("http://127.0.0.1:3000/v1/account/test/messages?path=\\Inbox")
                .headers(headers -> headers.setBearerAuth("7b99ff20d2de179f4bb6aab7b8f1fb65d494c77ca2f16b29d594bc4b79fc9d5f"))
                .retrieve()
                .bodyToMono(InboxResponse.class)
                .doOnNext(response -> {
                    List<Message> messages = response.getMessages();
                    if (messages == null) {
                        System.out.println("No messages found");
                    } else {
                        for (Message message : messages) {
                            System.out.println("-----------------    Message   -----------------------");
                            System.out.println(message.getFrom());
                            System.out.println(message.getId());
                            System.out.println(message.getSubject());
                            System.out.println(message.getDate());
                            System.out.println("\n\n");
                        }
                    }
                })
                .doOnError(error -> System.out.println(error.getMessage()));
    }

    public Mono<EmailSuccessResponse> sendMail(SendMailRequest sendMailRequest) {
        WebClient webClient = webClientBuilder.build();
        return webClient.post()
                .uri("http://127.0.0.1:3000/v1/account/test/submit")
                .header("Authorization", "Bearer 2b3d20380f92134d61ebdbc9660b20e8432fd60275d1194f822f84fc55d04f8a")
                .body(Mono.just(sendMailRequest), SendMailRequest.class)
                .retrieve()
                .bodyToMono(EmailSuccessResponse.class);
    }

    public boolean verifyConnection(VerifyConnectionRequest request) throws ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        VerifyAccount verifyAccount = new VerifyAccount();

        ImapConfiguration imapConfiguration = new ImapConfiguration();
        SmtpConfiguration smtpConfiguration = new SmtpConfiguration();

        Authentication imapAuth = new Authentication();
        imapAuth.setUser(request.getImap().getAuth().getUser());
        imapAuth.setPass(request.getImap().getAuth().getPass());
        imapConfiguration.setAuth(imapAuth);
        imapConfiguration.setSecure(request.getImap().isSecure());
        imapConfiguration.setPort(request.getImap().getPort());
        imapConfiguration.setHost(request.getImap().getHost());


        Authentication smtpAuth = new Authentication();
        smtpAuth.setUser(request.getSmtp().getAuth().getUser());
        smtpAuth.setPass(request.getSmtp().getAuth().getPass());
        smtpConfiguration.setAuth(smtpAuth);
        smtpConfiguration.setSecure(request.getSmtp().isSecure());
        smtpConfiguration.setPort(request.getSmtp().getPort());
        smtpConfiguration.setHost(request.getSmtp().getHost());

        verifyAccount.setImap(imapConfiguration);
        verifyAccount.setSmtp(smtpConfiguration);

        System.out.println(verifyAccount);

        VerifyAccountResponse response = accountApi.postV1Verifyaccount(60, verifyAccount);
        System.out.println(response.getImap().getSuccess());
        System.out.println(response.getSmtp().getSuccess());

        System.out.println(response);
        return Boolean.TRUE.equals(Objects.requireNonNull(response.getImap()).getSuccess()) &&
                Boolean.TRUE.equals(Objects.requireNonNull(response.getSmtp()).getSuccess());
    }

    public AccountResponse getAccount(String account) throws ApiException {
        AccountResponse response =  accountApi.getV1AccountAccount(account,60,false);
        System.out.println(response);
        System.out.println("Debug");
        return  response;
    }

    public DeleteRequestResponse deleteAccount(String account) throws ApiException {
        return accountApi.deleteV1AccountAccount(account,60);
    }

    public CreateTokenResponse generateToken(EmailEngineAccountTokenDTO emailEngineAccountTokenDTO) throws ApiException {
        CreateToken createToken = new CreateToken();
        createToken.setAccount(emailEngineAccountTokenDTO.getAccount());
        createToken.setDescription(emailEngineAccountTokenDTO.getDescription());
        List<TokenScope> tokenScopes = new ArrayList<>(Arrays.asList(TokenScope.API,TokenScope.SMTP));
        createToken.setScopes(tokenScopes);
        return accessTokensApi.postV1Token(60,createToken);
    }

    public DeleteTokenRequestResponse deleteToken(String token) throws ApiException {
        return accessTokensApi.deleteV1TokenToken(token,60);
    }


    public RequestAuthFormResponse getAuthenticationLink(EmailEngineAuthenticationFormDTO emailEngineAuthenticationFormDTO) throws ApiException {
        RequestAuthForm authForm = new RequestAuthForm();
        authForm.setAccount(emailEngineAuthenticationFormDTO.getAccount());
        authForm.setEmail(emailEngineAuthenticationFormDTO.getEmail());
        authForm.setName(emailEngineAuthenticationFormDTO.getName());
        authForm.setDelegated(emailEngineAuthenticationFormDTO.getDelegated());
        authForm.setRedirectUrl(emailEngineAuthenticationFormDTO.getRedirectUrl());
        authForm.setType(emailEngineAuthenticationFormDTO.getType());
        return accountApi.postV1AuthenticationForm(60,authForm);
    }

    public MessageDetails getMessage(GetMessageDTO getMessageDTO) throws ApiException {
//        MessageList list = messageApi.getV1AccountAccountMessages("test","Inbox",60,0,20,false);
        return messageApi.getV1AccountAccountMessageMessage(
                getMessageDTO.getAccount(),
                getMessageDTO.getMessage(),
                getMessageDTO.getXEETimeout(),
                getMessageDTO.getMaxBytes(),
                getMessageDTO.getTextType(),
                getMessageDTO.getWebSafeHtml(),
                getMessageDTO.getEmbedAttachedImages(),
                getMessageDTO.getPreProcessHtml(),
                getMessageDTO.getMarkAsSeen(),
                getMessageDTO.getDocumentStore()
        );
    }
    public String getAttachment (GetAttachmentDTO getAttachmentDTO) throws ApiException {
        return messageApi.getV1AccountAccountAttachmentAttachment(getAttachmentDTO.getAccount(),getAttachmentDTO.getAttachment(),getAttachmentDTO.getXEETimeout());
    }

    public SubmitMessageResponse sendMailSdk(SendMailDTO sendMailDTO) throws ApiException {
        SubmitMessage submitMessage = new SubmitMessage();
        FromAddress fromAddress = new FromAddress();
        fromAddress.setAddress(sendMailDTO.getFromAddress());
        fromAddress.setName(sendMailDTO.getFromName());
        ToAddress toAddress = new ToAddress();
        toAddress.setAddress(sendMailDTO.getToAddress());
        toAddress.setName(sendMailDTO.getToName());
        List<ToAddress> to = new ArrayList<>();
        to.add(toAddress);
        submitMessage.setTo(to);
        submitMessage.setFrom(fromAddress);
        submitMessage.setSubject(sendMailDTO.getSubject());
        submitMessage.setText(sendMailDTO.getText());
        submitMessage.setHtml(sendMailDTO.getHtml());
        submitMessage.setMailMerge(sendMailDTO.getMailMerge());

        System.out.println("Sending request:");
        System.out.println("Payload: " + submitMessage);

        SubmitMessageResponse response =  submitApi.postV1AccountAccountSubmit(sendMailDTO.getAccount(),60,submitMessage);
        System.out.println(response);
        return response;
    }

    public String getWebHooks(String payload) {
        return payload;
    }
}
