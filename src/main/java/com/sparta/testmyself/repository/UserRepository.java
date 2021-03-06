package com.sparta.testmyself.repository;

import com.sparta.testmyself.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakoId);
    Optional<User> findByEmail(String email);
}
