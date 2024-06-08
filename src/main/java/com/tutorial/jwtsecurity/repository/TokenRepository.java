package com.tutorial.jwtsecurity.repository;

import com.tutorial.jwtsecurity.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByKey(String key);
}
