package com.example.restservice.entities.users.repository;

import com.example.restservice.entities.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.accessToken = :accessToken, u.refreshToken = :refreshToken WHERE u.userName = :userName")
  void updateUserTokens(
    @Param("userName") String userName,
    @Param("accessToken") String accessToken,
    @Param("refreshToken") String refreshToken
  );

  @Query("SELECT u FROM User u WHERE u.userName = :userName")
  User findByUserName(@Param("userName") String userName);
}
