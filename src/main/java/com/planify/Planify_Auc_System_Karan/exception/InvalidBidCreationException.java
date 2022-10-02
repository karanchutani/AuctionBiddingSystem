package com.planify.Planify_Auc_System_Karan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is InvalidBidCreationException class.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBidCreationException extends RuntimeException {

    public InvalidBidCreationException(String exception) {
        super(exception);
    }
}
