package com.example.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Protocol {
    private Auth auth;
    private String host;
    private Integer port;
    private boolean secure;
}


