package com.planify.Planify_Auc_System_Karan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is AuctionNotFoundException class.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuctionNotFoundException extends RuntimeException {

    public AuctionNotFoundException(String exception) {
        super(exception);
    }
}
