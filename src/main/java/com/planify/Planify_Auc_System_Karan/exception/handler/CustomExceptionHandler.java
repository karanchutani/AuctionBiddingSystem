package com.planify.Planify_Auc_System_Karan.exception.handler;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.ErrorResponseDTO;
import com.planify.Planify_Auc_System_Karan.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Global controller for exception handling.
 * @author Karan
 */

@SuppressWarnings({BasicConstants.UNCHECKED, BasicConstants.RAW_TYPES})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This is handleAllExceptions method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.toString());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.SERVER_ERROR, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This is handleUserNotFoundException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is handleAuctionNotFoundException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(AuctionNotFoundException.class)
    public final ResponseEntity<Object> handleAuctionNotFoundException(AuctionNotFoundException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is handleInvalidBidCreationException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(InvalidBidCreationException.class)
    public final ResponseEntity<Object> handleInvalidBidCreationException(InvalidBidCreationException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is handleUsernameAlreadyExistException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public final ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is handleBidNotFoundException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(BidNotFoundException.class)
    public final ResponseEntity<Object> handleBidNotFoundException(BidNotFoundException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is handleInvalidRequestException method.
     * @param ex ex
     * @return object
     */

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        final ErrorResponseDTO error = new ErrorResponseDTO(BasicConstants.INVALID_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }




}
