package com.planify.Planify_Auc_System_Karan.repository;

import com.planify.Planify_Auc_System_Karan.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Optional<Auction> findByAuctionUsername(String username);

    Optional<Auction> findBySellerId(Long id);
}

