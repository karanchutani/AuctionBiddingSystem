package com.planify.Planify_Auc_System_Karan.dto;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Data
public class AutoRequestModel {

    private HttpMethod httpMethod;
    private String url;
    private HttpEntity<String> request;

    public AutoRequestModel(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }
    public AutoRequestModel(){}

}
