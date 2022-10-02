package com.planify.Planify_Auc_System_Karan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRequestDTO {

    private Integer lowestBidLimit;

    private Integer highestBidLimit;

    private String sellerUsername;

    private String auctionUsername;

    private Double participationCost;

    @JsonProperty
    private boolean isClosed;

}
