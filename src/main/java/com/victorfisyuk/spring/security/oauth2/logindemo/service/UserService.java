package com.victorfisyuk.spring.security.oauth2.logindemo.service;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    /**
     * Finds a user in the local database that is linked with the specified OAuth 2.0 user and
     * updates some of its attributes using OAuth 2.0 user attributes. If such the user does exist then
     * an exception is being thrown.
     *
     * @param provider OAuth 2.0 Provider used for the user authentication.
     * @param oauth2User OAuth 2.0 user that is being authenticated.
     * @return Local user linked with OAuth 2.0 user.
     * @throws OAuth2AuthenticationException if there is no user linked with OAuth 2.0 user.
     */
    User findAndUpdateUserByOAuth2User(AuthenticationProvider provider, OAuth2User oauth2User);

    /**
     * Finds a user in the local database that is linked with the specified OpenID Connect 1.0 user and
     * updates some of its attributes using OpenID Connect 1.0 user attributes. If such the user does exist then
     * an exception is being thrown.
     *
     * @param provider OpenID Connect 1.0 Provider used for the user authentication.
     * @param oidcUser OpenID Connect 1.0 user that is being authenticated.
     * @return Local user linked with OpenID Connect 1.0 user.
     * @throws OAuth2AuthenticationException if there is no user linked with OpenID Connect 1.0 user.
     */
    User findAndUpdateUserByOidcUser(AuthenticationProvider provider, OidcUser oidcUser);
}
