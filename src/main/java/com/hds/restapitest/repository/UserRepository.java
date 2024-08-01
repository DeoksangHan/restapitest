package com.hds.restapitest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hds.restapitest.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
 
	Optional<Users> findByEmail(String email);

}