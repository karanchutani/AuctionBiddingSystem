package com.planify.Planify_Auc_System_Karan.controller;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.AuctionRequestDTO;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.service.AuctionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is AuctionController class for creating and managing auctions.
 * @author Karan.
 */
@RestController
@RequestMapping("/auction")
public class AuctionController {

    /**
     * auctionService field.
     */
    @Autowired
    private AuctionService auctionService;

    /**
     * createAuction method for creating auctions.
     * @param auctionRequestDTO field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Create auction success.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = BasicConstants.AUCTION_CREATE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAuction(@RequestBody AuctionRequestDTO auctionRequestDTO){
        final Auction savedAuction = auctionService.createAuctionRequest(auctionRequestDTO);
        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .AUCTION_CREATE_SUCCESS,BasicConstants
                .SUCCESS,savedAuction), HttpStatus.CREATED);
    }

    /**
     * closeAuction method for closing auctions.
     * @param username field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Close auction success.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BasicConstants.AUCTION_CLOSE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping("/closeAuction")
    public ResponseEntity<ResponseDTO> closeAuction(@RequestParam(value = "auctionUsername", required = false) String username){
        final ResponseDTO savedAuctionResponse = auctionService.closeGivenAuction(username);
        return new ResponseEntity<>(savedAuctionResponse, HttpStatus.OK);
    }

}
