package com.planify.Planify_Auc_System_Karan.service.serviceImpl;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.exception.AuctionNotFoundException;
import com.planify.Planify_Auc_System_Karan.exception.InvalidRequestException;
import com.planify.Planify_Auc_System_Karan.exception.UserNotFoundException;
import com.planify.Planify_Auc_System_Karan.exception.UsernameAlreadyExistException;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.UserService;
import com.planify.Planify_Auc_System_Karan.utility.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This is UserServiceImpl class.
 * @author Karan.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

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
     * createUserService method for creating users.
     * @param user field.
     * @return user object.
     */
    @Override
    public User createUserService(final User user) {

        if(user.getUsername()==null || user.getUsername().isEmpty()){
            throw new InvalidRequestException(BasicConstants.USER_USERNAME_EMPTY);
        }

        if(userRepository
                .existsByUsername(user.getUsername())){
            throw new UsernameAlreadyExistException(BasicConstants
                    .USER_ALREADY_PRESENT+user.getUsername());

        }

        return userRepository.save(user);
    }

    /**
     * This updateUserAsPreferredUser() is used for updating user as preferred user.
     * @param username field.
     * @return user.
     */
    @Override
    public User updateUserAsPreferredUser(String username) {

        if(username==null || username.isEmpty()){
            throw new InvalidRequestException(BasicConstants.BUYER_USERNAME_EMPTY);
        }

        Optional<User> existingUserOptional = userRepository
                .findByUsernameAndIsSeller(username,false);

        if(existingUserOptional.isEmpty()){
            throw new UserNotFoundException(BasicConstants
                    .USER_UNAVAILABLE + username);
        }

        User savedUser = existingUserOptional.get();
        savedUser.setPreferredUser(true);

        return userRepository.saveAndFlush(savedUser);
    }

    /**
     * This getProfit() method is used for finding profit by corresponding seller.
     * @param username field.
     * @return Double.
     */
    @Override
    public Double getProfit(String username) {

        if(username==null || username.isEmpty()){
            throw new InvalidRequestException(BasicConstants.SELLER_USERNAME_EMPTY);
        }
        final Optional<User> existingUserOptional = userRepository.findByUsernameAndIsSeller(username, true);
        if(existingUserOptional.isEmpty()){
            throw new UserNotFoundException(BasicConstants.USER_UNAVAILABLE+username);
        }
        final Optional<Auction> existingAuctionOptional = auctionRepository.findBySellerId(existingUserOptional.get().getId());
        if(existingAuctionOptional.isEmpty()){
            throw new AuctionNotFoundException(BasicConstants.AUCTION_UNAVAILABLE_OR_NOT_REGISTERED+username);
        }
        final Auction auction = existingAuctionOptional.get();
        final Bid winnerBid = BasicUtil.getWinnerBid(auction.getBids());
        double profit = (auction.getParticipationCost()*0.2);
        if(winnerBid!=null){
            profit = winnerBid.getBidAmount()-(((double)(auction.getHighestBidLimit()+auction.getLowestBidLimit())/2))+profit;
        }
        System.out.println();
        System.out.println(BasicConstants.PROFIT_GET + username + BasicConstants.IS + profit);
        System.out.println();
        return profit;
    }

}
