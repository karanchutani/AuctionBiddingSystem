package com.planify.Planify_Auc_System_Karan.utility;

import com.planify.Planify_Auc_System_Karan.model.Bid;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is BasicUtil class.
 * @author Karan.
 */
public class BasicUtil {

    /**
     * This is synchronized method name getWinnerBid() for finding winners of auctions.
     * @param auctionBids field.
     * @return bid.
     */
    public synchronized static Bid getWinnerBid(Map<Long, Bid> auctionBids) {

        final Map<Integer, Bid> winnerMap = new TreeMap<>(Collections.reverseOrder());

        Bid bid = null;
        if(winnerMap.size()>0){
            winnerMap.clear();
        }
        auctionBids.forEach((k,v) ->{
            if(winnerMap.get(v.getBidAmount())==null){
                winnerMap.put(v.getBidAmount(),v);
            }
            else{
                if(!winnerMap.get(v.getBidAmount()).getUser().getPreferredUser() && v.getUser().getPreferredUser()){
                    winnerMap.remove(v.getBidAmount());
                    winnerMap.put(v.getBidAmount(),v);
                }
                else if(!(winnerMap.get(v.getBidAmount()).getUser().getPreferredUser() && ! v.getUser().getPreferredUser())){
                    winnerMap.remove(v.getBidAmount());
                }
            }
        });

        if(!winnerMap.isEmpty()){
            bid = winnerMap.values().iterator().next();
        }
        return bid;
    }
}
