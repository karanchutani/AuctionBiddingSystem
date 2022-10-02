package com.planify.Planify_Auc_System_Karan.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidRequestDTO {

    private Long bidId;

    private String buyerUsername;

    private String auctionUsername;

    private Integer amount;

}
