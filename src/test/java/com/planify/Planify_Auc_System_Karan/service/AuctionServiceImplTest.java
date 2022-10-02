package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.AuctionRequestDTO;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.serviceImpl.AuctionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
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
public class AuctionServiceImplTest {

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Test
    public void testCreateAuctionRequest(){

        final AuctionRequestDTO auctionRequestDTO = new AuctionRequestDTO();
        auctionRequestDTO.setAuctionUsername(BasicConstants.MY_RESPONSE_AUCTION);
        auctionRequestDTO.setSellerUsername(BasicConstants.MY_RESPONSE_USER);

        final Auction auction = mock(Auction.class);
        final Optional<Auction> optionalAuction = Optional.of(auction);

        final User user = mock(User.class);
        final Optional<User> optionalUser = Optional.of(user);

        lenient().when(userRepository
                .findByUsernameAndIsSeller(anyString(), anyBoolean())).thenReturn(optionalUser);

        lenient().when(auctionRepository.saveAndFlush(any(Auction.class))).thenReturn(auction);
        auctionService.createAuctionRequest(auctionRequestDTO);

        verify(userRepository, times(1)).findByUsernameAndIsSeller(anyString(), anyBoolean());
        verify(auctionRepository, times(1)).saveAndFlush(any(Auction.class));
    }

    @Test
    public void testCloseGivenAuction(){

        final String username = BasicConstants.MY_RESPONSE_AUCTION;
        final Auction auction = mock(Auction.class);
        final Optional<Auction> optionalAuction = Optional.of(auction);
        final Map<Long, Bid> mockMap = new HashMap<>();

        lenient().when(auction.getBids()).thenReturn(mockMap);
        lenient().when(auctionRepository
                .findByAuctionUsername(anyString())).thenReturn(optionalAuction);
        lenient().when(auctionRepository.saveAndFlush(any(Auction.class))).thenReturn(auction);
        final ResponseDTO responseDTO = auctionService.closeGivenAuction(username);

        assertEquals(responseDTO.getResponse(), auction);
    }


}
