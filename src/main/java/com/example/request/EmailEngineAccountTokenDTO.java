package com.example.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class EmailEngineAccountTokenDTO {
    private String account;
    private String description;
    private List<String> scopes = new ArrayList<>(Arrays.asList("api", "smtp"));
}
