package com.planify.Planify_Auc_System_Karan.service;

import com.planify.Planify_Auc_System_Karan.model.User;

public interface UserService {

    User createUserService(User user);

    User updateUserAsPreferredUser(String username);

    Double getProfit(String username);
}
