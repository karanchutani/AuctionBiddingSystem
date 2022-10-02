package com.planify.Planify_Auc_System_Karan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is UsernameAlreadyExistException class.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException(String exception) {
        super(exception);
    }
}
