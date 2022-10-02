package com.planify.Planify_Auc_System_Karan.controller;

import com.planify.Planify_Auc_System_Karan.constant.BasicConstants;
import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.User;
import com.planify.Planify_Auc_System_Karan.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is UserController class for creating and managing users.
 * @author Karan.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * userService field.
     */
    @Autowired
    private UserService userService;

    /**
     * createUser method for creating users.
     * @param user field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "User creation service", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = BasicConstants.USER_CREATE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createUser(@RequestBody User user){
        final User userResponse = userService.createUserService(user);
        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .USER_CREATE_SUCCESS,BasicConstants
                .SUCCESS,userResponse), HttpStatus.CREATED);

    }

    /**
     * preferredUser method for updating users as preferred users.
     * @param username field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Update user as preferred user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BasicConstants.USER_UPDATE_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @PutMapping("/preferredUser")
    public ResponseEntity<ResponseDTO> makePreferredUser(@RequestParam(value = "username") String username){
        final User userResponse = userService.updateUserAsPreferredUser(username);
        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .USER_UPDATE_SUCCESS,BasicConstants
                .SUCCESS,userResponse), HttpStatus.OK);
    }

    /**
     * getProfitForSeller method for finding profits of particular seller.
     * @param username field.
     * @return object of response DTO.
     */
    @ApiOperation(value = "Get profit for given seller.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BasicConstants.FETCH_PROFIT_SUCCESS),
            @ApiResponse(code = 403, message = BasicConstants.FORBIDDEN),
            @ApiResponse(code = 400, message = BasicConstants.BAD_REQUEST)
    }
    )
    @GetMapping("/getProfit")
    public ResponseEntity<ResponseDTO> getProfitForSeller(@RequestParam(value = "username", required = false) String username){
        final Double profit = userService.getProfit(username);
        return new ResponseEntity<>(new ResponseDTO(BasicConstants
                .FETCH_PROFIT_SUCCESS,BasicConstants
                .SUCCESS,profit), HttpStatus.OK);
    }

}
