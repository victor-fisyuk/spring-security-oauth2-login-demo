package com.victorfisyuk.spring.security.oauth2.logindemo.service;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.principal.LocalUserLinkedOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link OAuth2UserService} that delegates calls to the default OAuth 2.0 user service
 * implementation and then validates that there is a user in the local database linked with the appropriate
 * OAuth 2.0 user. If such the user exists then this service updates some of its attributes using OAuth 2.0
 * user attributes.
 * <p/>
 * This service is being called when a user is being authenticated using some OAuth 2.0 Provider (e.g., GitHub).
 *
 * @see LocalUserLinkedOidcUserService
 * @see DefaultOAuth2UserService
 */
@Service
public class LocalUserLinkedOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UserService userService;

    @Autowired
    public LocalUserLinkedOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oauth2User = delegate.loadUser(userRequest);

        var clientAuthenticationProvider = AuthenticationProvider.valueOf(
                userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        var user = userService.findAndUpdateUserByOAuth2User(clientAuthenticationProvider, oauth2User);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        return LocalUserLinkedOAuth2User.of(user, oauth2User, userNameAttributeName);
    }
}
