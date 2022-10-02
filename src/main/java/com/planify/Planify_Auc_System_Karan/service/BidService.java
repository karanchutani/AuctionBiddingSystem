package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.dto.BidRequestDTO;
import com.planify.Planify_Auc_System_Karan.model.Bid;

public interface BidService {

    Bid createBidService(BidRequestDTO bidRequestDTO);

    Bid updateBidService(BidRequestDTO bidRequestDTO);

    Bid withdrawBidService(Long bidId);
}
