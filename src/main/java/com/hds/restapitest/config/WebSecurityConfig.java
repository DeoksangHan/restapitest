package com.hds.restapitest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hds.restapitest.service.UserDetailService;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 1. 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
        //        .requestMatchers("/static/**");
                .antMatchers("/static/**");
    }

    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	/* book 에 ....
    	return http
                .authorizeRequests(auth -> auth  // 인증, 인가 설정
                    .requestMatchers(
                    		new AntPathRequestMatcher("/login"),
                    		new AntPathRequestMatcher("/signup"),
                    		new AntPathRequestMatcher("/user")
                    ).permitAll()
                    .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin  // 폼 기반 로그인 설정
                    .loginPage("/login")
                    .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout // 로그아웃 설정
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 
                .build();
        */    	
    	// github 에.. 
    	return http
                .authorizeRequests() // 인증, 인가 설정
//                    .requestMatchers("/login", "/signup", "/user", "/api/**").permitAll()
                    .antMatchers("/login", "/signup", "/user", "/api/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()  // 폼 기반 로그인 설정
                    .loginPage("/login")
                    .defaultSuccessUrl("/articles")
                .and()
                .logout() // 로그아웃 설정
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                .and()
                .csrf().disable() // csrf 비활성화 
                .build();
        
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {

    	return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // 패스워드 인코더로 사용 할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}