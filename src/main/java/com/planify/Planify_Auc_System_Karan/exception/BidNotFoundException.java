package com.planify.Planify_Auc_System_Karan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is BidNotFoundException class.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BidNotFoundException extends RuntimeException {

    public BidNotFoundException(String exception) {
        super(exception);
    }
}
