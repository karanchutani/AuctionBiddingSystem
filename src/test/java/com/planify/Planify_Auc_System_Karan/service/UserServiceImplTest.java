package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.model.Auction;
import com.planify.Planify_Auc_System_Karan.model.Bid;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.repository.AuctionRepository;
import com.planify.Planify_Auc_System_Karan.repository.UserRepository;
import com.planify.Planify_Auc_System_Karan.service.serviceImpl.UserServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Test
    public void testCreateUserService(){

        final User user = new User();
        user.setUsername(BasicConstants.TEST_USERNAME);
        lenient().when(userRepository
                .existsByUsername(anyString())).thenReturn(false);
        final User responseUser = new User();
        responseUser.setUsername(BasicConstants.MY_RESPONSE_USER);
        lenient().when(userRepository.save(user)).thenReturn(responseUser);

        final User savedUser = userService.createUserService(user);
        assertEquals(savedUser.getUsername(), BasicConstants.MY_RESPONSE_USER);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByUsername(anyString());

    }

    @Test
    public void testGetProfit(){

        final String username = BasicConstants.MY_RESPONSE_USER;
        final User user = mock(User.class);
        final Auction auction = mock(Auction.class);
        final Optional<User> optionalUser = Optional.of(user);
        final Optional<Auction> optionalAuction = Optional.of(auction);

        lenient().when(userRepository.findByUsernameAndIsSeller(anyString(), anyBoolean())).thenReturn(optionalUser);
        lenient().when(auctionRepository.findBySellerId(anyLong())).thenReturn(optionalAuction);

        final Map<Long, Bid> mockMap = new HashMap<>();
        lenient().when(auction.getBids()).thenReturn(mockMap);
        lenient().when(auction.getParticipationCost()).thenReturn(1.0);
        final Double returnedProfit = userService.getProfit(username);
        assertEquals(returnedProfit,0.2);
        verify(userRepository, times(1)).findByUsernameAndIsSeller(anyString(), anyBoolean());
        verify(auctionRepository, times(1)).findBySellerId(anyLong());

    }
}
