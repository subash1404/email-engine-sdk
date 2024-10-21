package com.example.request;

import lombok.Data;

@Data
public class GetAttachmentDTO {
    private String account;
    private String attachment;
    private Integer xEETimeout = 60;
}
