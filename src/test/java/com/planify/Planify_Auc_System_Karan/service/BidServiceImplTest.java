package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.BidRequestDTO;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.BidRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.serviceImpl.BidServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BidServiceImplTest {

    @InjectMocks
    private BidServiceImpl bidService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateBidService(){

        final BidRequestDTO bidRequestDTO = new BidRequestDTO();
        bidRequestDTO.setBuyerUsername(BasicConstants.MY_RESPONSE_USER);
        bidRequestDTO.setAuctionUsername(BasicConstants.MY_RESPONSE_AUCTION);
        bidRequestDTO.setAmount(5);
        final User user = mock(User.class);
        final Optional<User> optionalUser = Optional.of(user);
        final Auction auction = mock(Auction.class);
        final Optional<Auction> optionalAuction = Optional.of(auction);

        lenient().when(userRepository
                .findByUsernameAndIsSeller(anyString(), anyBoolean())).thenReturn(optionalUser);

        lenient().when(auctionRepository
                .findByAuctionUsername(anyString())).thenReturn(optionalAuction);

        lenient().when(auction.getHighestBidLimit()).thenReturn(10);
        lenient().when(auction.getLowestBidLimit()).thenReturn(1);
        lenient().when(auction.isClosed()).thenReturn(false);

        final Bid bid = mock(Bid.class);
        lenient().when(bidRepository.saveAndFlush(any(Bid.class))).thenReturn(bid);
        lenient().when(bidRepository.checkPreferredUserEligibility(anyLong())).thenReturn(1);
        final Map<Long, Bid> innerMap = new HashMap<>();
        lenient().when(auction.getBids()).thenReturn(innerMap);
        lenient().when(bid.getId()).thenReturn(1L);
        lenient().when(auctionRepository.saveAndFlush(any(Auction.class))).thenReturn(auction);

        final Bid returnedBid = bidService.createBidService(bidRequestDTO);
        assertEquals(returnedBid,bid);
        verify(userRepository, times(1)).findByUsernameAndIsSeller(anyString(), anyBoolean());
        verify(auctionRepository, times(1)).findByAuctionUsername(anyString());
        verify(bidRepository, times(1)).checkPreferredUserEligibility(anyLong());
        verify(bidRepository, times(1)).saveAndFlush(any(Bid.class));
        verify(auctionRepository, times(1)).saveAndFlush(any(Auction.class));

    }

    @Test
    public void testUpdateBidService(){
        final BidRequestDTO bidRequestDTO = new BidRequestDTO();
        bidRequestDTO.setBidId(1L);
        bidRequestDTO.setAmount(1);
        final Bid bid = mock(Bid.class);
        final Optional<Bid> optionalBid = Optional.of(bid);
        final Auction auction = mock(Auction.class);
        final Optional<Auction> optionalAuction = Optional.of(auction);
        final Map<Long, Bid> mockMap = new HashMap<>();

        lenient().when(bidRepository.findById(anyLong())).thenReturn(optionalBid);
        doNothing().when(bid).setBidAmount(anyInt());
        lenient().when(bidRepository.saveAndFlush(any(Bid.class))).thenReturn(bid);

        lenient().when(auctionRepository
                .findById(anyLong())).thenReturn(optionalAuction);

        lenient().when(auction.getBids()).thenReturn(mockMap);
        lenient().when(bid.getId()).thenReturn(1L);
        lenient().when(auctionRepository.saveAndFlush(any(Auction.class))).thenReturn(auction);
        final Bid returnedBid = bidService.updateBidService(bidRequestDTO);

        assertEquals(returnedBid,bid);
        verify(auctionRepository, times(1)).findById(anyLong());
        verify(bidRepository, times(1)).findById(anyLong());
        verify(bidRepository, times(1)).saveAndFlush(any(Bid.class));
        verify(auctionRepository, times(1)).saveAndFlush(any(Auction.class));

    }

    @Test
    public void testWithdrawBidService(){

        final Long bidId = 1L;
        final Bid bid = mock(Bid.class);
        final Optional<Bid> optionalBid = Optional.of(bid);
        final Auction auction = mock(Auction.class);
        final Optional<Auction> optionalAuction = Optional.of(auction);
        final Map<Long, Bid> mockMap = mock(Map.class);

        lenient().when(bidRepository.findById(anyLong())).thenReturn(optionalBid);
        lenient().when(auctionRepository
                .findById(anyLong())).thenReturn(optionalAuction);

        lenient().when(auction.getBids()).thenReturn(mockMap);
        lenient().when(mockMap.remove(any())).thenReturn(bid);
        lenient().when(auctionRepository.saveAndFlush(any(Auction.class))).thenReturn(auction);
        doNothing().when(bidRepository).delete(any(Bid.class));

        final Bid returnedBid = bidService.withdrawBidService(bidId);

        assertEquals(returnedBid,bid);
        verify(auctionRepository, times(1)).findById(anyLong());
        verify(bidRepository, times(1)).findById(anyLong());
        verify(auctionRepository, times(1)).saveAndFlush(any(Auction.class));
        verify(bidRepository, times(1)).delete(any(Bid.class));

    }

}
