package com.example.building_backdrop.data.repository;

import com.example.building_backdrop.data.model.AccountUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AccountUser, String> {
    @Override
    Optional<AccountUser> findById(String s);
    Optional<AccountUser> findUserByAccountNumber(String s);
    @Transactional
    @Modifying
    @Query("UPDATE AccountUser accountUser " +
            "SET accountUser.is_verified = true " +
            "WHERE accountUser.accountName = ?1")
    void updateUser(String username);
}
