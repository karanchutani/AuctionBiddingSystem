package com.planify.Planify_Auc_System_Karan.service.serviceImpl;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.BidRequestDTO;
import com.planify.Planify_Auc_System_Karan.exception.*;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.BidRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This is BidServiceImpl class.
 * @author Karan.
 */
@Service
@Transactional
public class BidServiceImpl implements BidService {

    /**
     * bidRepository field.
     */
    @Autowired
    private BidRepository bidRepository;

    /**
     * auctionRepository field.
     */
    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * userRepository field.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * This method createBidService() is used for creating the bids.
     * @param bidRequestDTO field.
     * @return bid.
     */
    @Override
    public Bid createBidService(BidRequestDTO bidRequestDTO) {

        if(bidRequestDTO.getAuctionUsername()==null || bidRequestDTO.getAuctionUsername().isEmpty()){
            throw new InvalidRequestException(BasicConstants.AUCTION_USERNAME_EMPTY);
        }

        if(bidRequestDTO.getBuyerUsername()==null || bidRequestDTO.getBuyerUsername().isEmpty()){
            throw new InvalidRequestException(BasicConstants.BUYER_USERNAME_EMPTY);
        }

        final Optional<User> existingUserOptional = userRepository
                .findByUsernameAndIsSeller(bidRequestDTO.getBuyerUsername(),false);
        final Optional<Auction> existingAuctionOptional = auctionRepository
                .findByAuctionUsername(bidRequestDTO.getAuctionUsername());

        if(existingUserOptional.isEmpty()){
            throw new UserNotFoundException(BasicConstants.USER_UNAVAILABLE + bidRequestDTO.getBuyerUsername());
        }
        if(existingAuctionOptional.isEmpty()){
            throw new AuctionNotFoundException(BasicConstants.AUCTION_UNAVAILABLE + bidRequestDTO.getAuctionUsername());
        }

        final User existingUser = existingUserOptional.get();
        final Auction existingAuction = existingAuctionOptional.get();

        if(!(bidRequestDTO.getAmount()<=existingAuction.getHighestBidLimit()
                && bidRequestDTO.getAmount()>=existingAuction.getLowestBidLimit())){

            throw new InvalidBidCreationException(BasicConstants.BID_LIMIT_OUTSIDE_EXCEPTION);
        }

        if(existingAuction.isClosed()){
            throw new AuctionNotFoundException(BasicConstants.AUCTION_CLOSED);
        }
        final Bid bid = createBidServiceRequest(existingAuction,existingUser,bidRequestDTO);
        final Bid returnedBid = bidRepository.saveAndFlush(bid);

        int partInAuctionsCount = bidRepository.checkPreferredUserEligibility(existingUser.getId());
        if(partInAuctionsCount>2){
            if(!existingUser.getPreferredUser()) {
                existingUser.setPreferredUser(true);
                userRepository.saveAndFlush(existingUser);
            }
        }
        existingAuction.getBids().put(returnedBid.getId(), returnedBid);
        auctionRepository.saveAndFlush(existingAuction);

        return returnedBid;
    }

    /**
     * This is updateBidService() method.
     * @param bidRequestDTO field.
     * @return bid object.
     */
    @Override
    public Bid updateBidService(BidRequestDTO bidRequestDTO) {

        if(bidRequestDTO.getBidId()==null){
            throw new InvalidRequestException(BasicConstants.BID_ID_NULL);
        }

        final Optional<Bid> existingBidOptional = bidRepository.findById(bidRequestDTO.getBidId());

        if(existingBidOptional.isEmpty()){
            throw new BidNotFoundException(BasicConstants.BID_UNAVAILABLE + bidRequestDTO.getBidId());
        }

        final Bid savedBid = existingBidOptional.get();
        savedBid.setBidAmount(bidRequestDTO.getAmount());
        final Bid returnedBid = bidRepository.saveAndFlush(savedBid);
        final Auction existingAuction = auctionRepository
                .findById(returnedBid.getAuctionId()).get();

        existingAuction.getBids().put(returnedBid.getId(), returnedBid);
        auctionRepository.saveAndFlush(existingAuction);

        return returnedBid;
    }

    /**
     * This method withdrawBidService() is used for withdraw bids.
     * @param bidId field.
     * @return bid.
     */
    @Override
    public Bid withdrawBidService(Long bidId) {

        if(bidId==null){
            throw new InvalidRequestException(BasicConstants.BID_ID_NULL);
        }
        final Optional<Bid> existingBidOptional = bidRepository.findById(bidId);

        if(existingBidOptional.isEmpty()){
            throw new BidNotFoundException(BasicConstants.BID_UNAVAILABLE + bidId);
        }

        final Bid savedBid = existingBidOptional.get();
        final Auction existingAuction = auctionRepository
                .findById(savedBid.getAuctionId()).get();

        existingAuction.getBids().remove(savedBid.getId());
        auctionRepository.saveAndFlush(existingAuction);
        bidRepository.delete(savedBid);

        return savedBid;
    }

    /**
     * createBidServiceRequest() method for creating new requested bid.
     * @param existingAuction field.
     * @param existingUser field.
     * @param bidRequestDTO field.
     * @return bid.
     */
    private Bid createBidServiceRequest(Auction existingAuction, User existingUser, BidRequestDTO bidRequestDTO) {

        final Bid bid = new Bid();
        bid.setAuctionId(existingAuction.getId());
        bid.setUser(existingUser);
        bid.setBidAmount(bidRequestDTO.getAmount());
        return bid;
    }

}
