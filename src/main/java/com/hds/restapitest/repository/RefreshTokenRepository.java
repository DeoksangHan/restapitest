package com.hds.restapitest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hds.restapitest.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
 
	Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}