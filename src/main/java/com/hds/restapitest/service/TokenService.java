package com.hds.restapitest.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.hds.restapitest.config.jwt.TokenProvider;
import com.hds.restapitest.domain.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Users user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}