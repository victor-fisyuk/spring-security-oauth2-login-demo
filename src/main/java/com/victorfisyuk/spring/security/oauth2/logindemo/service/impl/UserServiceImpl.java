package com.victorfisyuk.spring.security.oauth2.logindemo.service.impl;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.User;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo.OAuth2UserInfo;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo.OAuth2UserInfoFactory;
import com.victorfisyuk.spring.security.oauth2.logindemo.repository.UserRepository;
import com.victorfisyuk.spring.security.oauth2.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    public static final String USER_NOT_FOUND_ERROR_CODE = "user_not_found";
    public static final String USER_NOT_FOUND_DESCRIPTION = "Cannot find local user for provider user %s";

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findAndUpdateUserByOAuth2User(AuthenticationProvider provider, OAuth2User oauth2User) {
        var userInfo = OAuth2UserInfoFactory.create(provider, oauth2User.getAttributes());
        return updateUserUsingOAuth2User(provider, oauth2User.getName(), userInfo);
    }

    @Override
    public User findAndUpdateUserByOidcUser(AuthenticationProvider provider, OidcUser oidcUser) {
        var userInfo = OAuth2UserInfoFactory.create(provider, oidcUser.getAttributes());
        return updateUserUsingOAuth2User(provider, oidcUser.getName(), userInfo);
    }

    private User updateUserUsingOAuth2User(AuthenticationProvider provider, String providerId, OAuth2UserInfo userInfo) {
        var user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error(USER_NOT_FOUND_ERROR_CODE,
                        String.format(USER_NOT_FOUND_DESCRIPTION, providerId), null)));

        // Update the local user using the information from the authentication provider
        user.setPictureUrl(userInfo.getPictureUrl());
        return user;
    }
}
