package com.example.request;

import lombok.Data;
import org.openapitools.client.model.MailMergeListEntry;

import java.util.List;

@Data
public class SendMailDTO {
    private String account;
    private String fromAddress;
    private String toAddress;
    private String fromName = null;
    private String toName = null;
    private String subject = null;
    private String text = null;
    private String html = null;
    private List<MailMergeListEntry> mailMerge = null;
}
