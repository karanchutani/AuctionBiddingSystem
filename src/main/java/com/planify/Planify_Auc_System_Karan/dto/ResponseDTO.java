package com.planify.Planify_Auc_System_Karan.dto;

import com.planify.Planify_Auc_System_Karan.model.Auction;
import lombok.*;

@ToString
@NoArgsConstructor
@Data
public class ResponseDTO {

    private String message;
    private String extraMessage;
    private String status;
    private Object response;

    public ResponseDTO(String message, String status, Object response) {
        this.message = message;
        this.status = status;
        this.response = response;
    }

    public ResponseDTO(String message, String extraMessage, String status, Object response) {
        this.message = message;
        this.extraMessage = extraMessage;
        this.status = status;
        this.response = response;
    }
}
