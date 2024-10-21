package com.example.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetMessageDTO {
    private String account;
    private String message;
    private Integer xEETimeout = 60;
    private Integer maxBytes = null;
    private String textType = null;
    private Boolean webSafeHtml = false;
    private Boolean embedAttachedImages = false;
    private Boolean preProcessHtml = false;
    private Boolean markAsSeen = false;
    private Boolean documentStore = false;

    public GetMessageDTO(String account, String message) {
        this.account = account;
        this.message = message;
    }
}
