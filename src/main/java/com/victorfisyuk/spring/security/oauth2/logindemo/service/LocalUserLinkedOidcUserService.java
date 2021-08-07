package com.victorfisyuk.spring.security.oauth2.logindemo.service;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.principal.LocalUserLinkedOidcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link OAuth2UserService} that delegates calls to the default OpenID Connect 1.0 user service
 * implementation and then validates that there is a user in the local database linked with the appropriate
 * OpenID Connect 1.0 user. If such the user exists then this service updates some of its attributes using
 * OpenID Connect 1.0 user attributes.
 * <p/>
 * This service is being called when a user is being authenticated using some OpenID Connect 1.0 Provider
 * (e.g., Google).
 *
 * @see LocalUserLinkedOAuth2UserService
 * @see OidcUserService
 */
@Service
public class LocalUserLinkedOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final OidcUserService delegate = new OidcUserService();
    private final UserService userService;

    @Autowired
    public LocalUserLinkedOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        var oidcUser = delegate.loadUser(userRequest);

        var clientAuthenticationProvider = AuthenticationProvider.valueOf(
                userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        var user = userService.findAndUpdateUserByOidcUser(clientAuthenticationProvider, oidcUser);
        return LocalUserLinkedOidcUser.of(user, oidcUser);
    }
}
