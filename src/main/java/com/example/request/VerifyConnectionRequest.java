package com.example.request;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.client.model.Authentication;
import org.openapitools.client.model.ImapConfiguration;

@Getter
@Setter
public class VerifyConnectionRequest {
    private Protocol imap;
    private Protocol smtp;
}


