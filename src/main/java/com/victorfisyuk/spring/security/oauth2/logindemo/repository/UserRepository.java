package com.victorfisyuk.spring.security.oauth2.logindemo.repository;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(AuthenticationProvider provider, String providerId);
}
