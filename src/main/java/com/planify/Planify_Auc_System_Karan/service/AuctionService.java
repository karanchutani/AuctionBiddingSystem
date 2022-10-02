package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.dto.AuctionRequestDTO;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.Auction;

public interface AuctionService {
    Auction createAuctionRequest(AuctionRequestDTO auctionRequestDTO);

    ResponseDTO closeGivenAuction(String id);
}
