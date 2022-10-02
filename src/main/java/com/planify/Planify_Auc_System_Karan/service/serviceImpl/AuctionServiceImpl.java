package com.planify.Planify_Auc_System_Karan.service.serviceImpl;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.AuctionRequestDTO;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.exception.AuctionNotFoundException;
import com.planify.Planify_Auc_System_Karan.exception.InvalidRequestException;
import com.planify.Planify_Auc_System_Karan.exception.UserNotFoundException;
import com.planify.Planify_Auc_System_Karan.exception.UsernameAlreadyExistException;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.AuctionService;
import com.planify.Planify_Auc_System_Karan.utility.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This is AuctionServiceImpl class.
 * @author Karan.
 */
@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

    /**
     * userRepository field.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * auctionRepository field.
     */
    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * This is createAuctionRequest() method for creating auctions.
     * @param auctionRequestDTO field.
     * @return auction object.
     */
    @Override
    public Auction createAuctionRequest(final AuctionRequestDTO auctionRequestDTO) {

        if(auctionRequestDTO.getAuctionUsername()==null || auctionRequestDTO.getAuctionUsername().isEmpty()){
            throw new InvalidRequestException(BasicConstants.AUCTION_USERNAME_EMPTY);
        }

        if(auctionRequestDTO.getSellerUsername()==null || auctionRequestDTO.getSellerUsername().isEmpty()){
            throw new InvalidRequestException(BasicConstants.SELLER_USERNAME_EMPTY);
        }

        final Optional<Auction> auctionOptional = auctionRepository
                .findByAuctionUsername(auctionRequestDTO.getAuctionUsername());

        if(auctionOptional.isPresent()){
            throw new UsernameAlreadyExistException(BasicConstants
                    .AUCTION_ALREADY_EXIST_EXCEPTION + auctionRequestDTO.getAuctionUsername());

        }

        final Optional<User> userOptional = userRepository
                .findByUsernameAndIsSeller(auctionRequestDTO.getSellerUsername(),true);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException(BasicConstants.USER_UNAVAILABLE + auctionRequestDTO.getSellerUsername());
        }

        final Auction auction = getAuctionRequest(userOptional.get(),auctionRequestDTO);
        return auctionRepository.saveAndFlush(auction);
    }

    /**
     * This is closeGivenAuction() method for closing auctions
     * @param username field.
     * @return auction object.
     */
    @Override
    public ResponseDTO closeGivenAuction(final String username) {

        if(username==null || username.isEmpty()){
            throw new InvalidRequestException(BasicConstants
                    .AUCTION_USERNAME_EMPTY);
        }

        final Optional<Auction> savedAuctionOptional = auctionRepository
                .findByAuctionUsername(username);

        if(savedAuctionOptional.isEmpty()){
            throw new AuctionNotFoundException(BasicConstants
                    .AUCTION_UNAVAILABLE+username);

        }

        final Auction auction = savedAuctionOptional.get();
        final Bid bid = BasicUtil.getWinnerBid(auction.getBids());
        String winnerMessage = BasicConstants.NO_WINNER;
        if(bid!=null) {
            final String buyerName = bid.getUser().getUsername();
            winnerMessage = BasicConstants.WINNER_IS + buyerName;
            System.out.println();
            System.out.println(winnerMessage);
            System.out.println();
        }
        else{
            System.out.println(winnerMessage);
        }
        auction.setClosed(true);
        final Auction savedAuction  = auctionRepository.saveAndFlush(auction);

        return new ResponseDTO(BasicConstants
                .AUCTION_CLOSE_SUCCESS, winnerMessage, BasicConstants
                .SUCCESS, savedAuction);

    }

    /**
     * This is getAuctionRequest() for creating new  auction request.
     * @param user field.
     * @param auctionRequestDTO field.
     * @return auction object.
     */
    private Auction getAuctionRequest(final User user, AuctionRequestDTO auctionRequestDTO) {

        final Auction auction = new Auction();
        auction.setClosed(false);
        auction.setHighestBidLimit(auctionRequestDTO.getHighestBidLimit());
        auction.setLowestBidLimit(auctionRequestDTO.getLowestBidLimit());
        auction.setSellerId(user.getId());
        auction.setAuctionUsername(auctionRequestDTO.getAuctionUsername());
        auction.setParticipationCost(auctionRequestDTO.getParticipationCost());
        return auction;
    }
}
