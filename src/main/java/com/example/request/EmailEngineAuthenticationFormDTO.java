package com.example.request;

import lombok.Data;

@Data
public class EmailEngineAuthenticationFormDTO {
    private String account;
    private String email;
    private String name;
    private Boolean delegated;
    private String redirectUrl;
    private String type;
}
