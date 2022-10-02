package com.planify.Planify_Auc_System_Karan.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * DTO for Error Response Data.
 * @author Karan
 */

@Data
@XmlRootElement(name = "error")
public class ErrorResponseDTO {

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

    /**
     * ErrorResponseDTO constructor.
     * @param message field.
     * @param details field.
     */
    public ErrorResponseDTO(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

}
