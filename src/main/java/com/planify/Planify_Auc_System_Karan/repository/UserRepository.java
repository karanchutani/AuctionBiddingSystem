package com.planify.Planify_Auc_System_Karan.repository;

import com.planify.Planify_Auc_System_Karan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndIsSeller(String username, boolean isSellerUser);

    boolean existsByUsername(String username);
}
