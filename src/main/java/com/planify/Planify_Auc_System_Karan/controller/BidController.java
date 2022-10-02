package com.planify.Planify_Auc_System_Karan.controller;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.BidRequestDTO;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.service.BidService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is BidController class for creating and managing bids.
 * @author Karan.
 */
@RestController
@RequestMapping("/bid")
public class BidController {

    /**
     * bidService field.
     */
    @Autowired
    private BidService bidService;

    /**
     * createBid method for creating bids.
     * @param bidRequestDTO field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Create bid success.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = BasicConstants.BID_CREATE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createBid(@RequestBody BidRequestDTO bidRequestDTO){

        final Bid bidResponse = bidService
                .createBidService(bidRequestDTO);

        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .BID_CREATE_SUCCESS,BasicConstants
                .SUCCESS,bidResponse), HttpStatus.CREATED);

    }

    /**
     * updateBid method for updating bids.
     * @param bidRequestDTO field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Bid updated successfully.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BasicConstants.BID_UPDATE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateBid(@RequestBody BidRequestDTO bidRequestDTO){

        final Bid bidResponse = bidService
                .updateBidService(bidRequestDTO);

        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .BID_UPDATE_SUCCESS,BasicConstants
                .SUCCESS,bidResponse), HttpStatus.OK);

    }

    /**
     * withdrawBid method for withdrawing bids.
     * @param bidId field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Bid withdraw success.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BasicConstants.BID_DELETE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping(value = "/withdraw/bid/{bidId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> withdrawBid(@PathVariable(value = "bidId", required = false) Long bidId){

        final Bid bidResponse = bidService
                .withdrawBidService(bidId);

        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .BID_DELETE_SUCCESS,BasicConstants
                .SUCCESS,bidResponse), HttpStatus.OK);

    }

}
