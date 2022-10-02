package com.planify.Planify_Auc_System_Karan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "auction")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Auction extends Auditable<String> {

    @Column(unique = true)
    private String auctionUsername;

    private Integer lowestBidLimit;

    private Integer highestBidLimit;

    private Long sellerId;

    private boolean isClosed;

    private Double participationCost;

    @MapKey(name = "id")
    @ManyToMany
    private Map<Long, Bid> bids = new HashMap<>();

}
