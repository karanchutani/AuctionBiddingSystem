package com.planify.Planify_Auc_System_Karan.repository;

import com.planify.Planify_Auc_System_Karan.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT COUNT( DISTINCT b.auction_id ) as partInAuctions from bid b where b.user_id = ?1", nativeQuery = true)
    int checkPreferredUserEligibility(Long id);
}
