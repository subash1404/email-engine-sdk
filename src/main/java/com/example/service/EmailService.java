package com.example.service;

import com.example.request.SendMailRequest;
import com.example.response.EmailSuccessResponse;
import com.example.response.InboxResponse;
import com.example.response.Message;
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

//    private SubmitApi submitApi;

    @PostConstruct
//    public void init() {
//        ApiClient apiClient = new ApiClient();
////        apiClient.selectHeaderContentType(new String[]{"*/*"});
//        apiClient.setBasePath("http://localhost:3000");
////        apiClient.setApiKeyPrefix("Bearer ");
//        apiClient.setApiKey("d50ecb0ed2a242291a6262e62d771562b75e708605106eba804041ec46e19ed6");
////        apiClient.setAccessToken("Bearer fe0c3b98f532373ddd4 4394f60a93f38d611364c77e00f1113a91b18edf04778");
//        submitApi = new SubmitApi(apiClient);
//    }

    public Mono<InboxResponse> fetchInbox() {
        WebClient webClient = webClientBuilder.build();
        return webClient.get()
                .uri("http://127.0.0.1:3000/v1/account/test/messages?path=\\Inbox")
                .headers(headers -> headers.setBearerAuth("2b3d20380f92134d61ebdbc9660b20e8432fd60275d1194f822f84fc55d04f8a"))
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

//    public SubmitMessageResponse sendMailSdk() throws ApiException {
//        ApiClient apiClient = submitApi.getApiClient();
//        Authentication auth = apiClient.getAuthentication("bearerAuth");
//        Map<String,String> headers = new HashMap<>();
//        headers.put("Content-Type","application/json");
////        headers.put("Authorization", "Bearer fe0c3b98f532373ddd4 4394f60a93f38d611364c77e00f1113a91b18edf04778");
//        SubmitMessage submitMessage = new SubmitMessage();
//        submitMessage.setHeaders(headers);
//        FromAddress fromAddress = new FromAddress();
//        fromAddress.setAddress("prajwalpandi1@gmail.com");
//        fromAddress.setName("Prajwal");
//        ToAddress toAddress = new ToAddress();
//        toAddress.setAddress("subash.v@superops.com");
//        toAddress.setName("Subash");
//        List<ToAddress> to = new ArrayList<>();
//        to.add(toAddress);
//        submitMessage.setTo(to);
//        submitMessage.setFrom(fromAddress);
//        submitMessage.setSubject("Test email");
//        submitMessage.setText("Hello");
//        submitMessage.setHtml("<p>This is html content</p>");
//
//        submitMessage.setCc(null);
//        submitMessage.setBcc(null);
//        submitMessage.setMailMerge(null);
//        submitMessage.setAttachments(null);
//        submitMessage.sendAt(null);
//        System.out.println("Sending request:");
//        System.out.println("Payload: " + submitMessage);
//
//        SubmitMessageResponse response =  submitApi.postV1AccountAccountSubmit("test",60,submitMessage);
//        System.out.println(response);
//        return response;
//    }

    public String getWebHooks(String payload) {
        return payload;
    }
}
