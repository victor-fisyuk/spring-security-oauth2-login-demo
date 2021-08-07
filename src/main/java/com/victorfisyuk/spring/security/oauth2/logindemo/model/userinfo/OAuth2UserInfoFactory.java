package com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo;

import com.victorfisyuk.spring.security.oauth2.logindemo.model.AuthenticationProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * A factory to create {@code OAuth2UserInfo} instances.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfoFactory {
    /**
     * Constructs a {@code OAuth2UserInfo} based on an OAuth 2.0 / OpenID Connect 1.0 provider and token attributes.
     *
     * @param provider the OAuth 2.0 / OpenID Connect 1.0 provider
     * @param attributes the token attributes
     * @return the created {@code OAuth2UserInfo}
     */
    public static OAuth2UserInfo create(AuthenticationProvider provider, Map<String, Object> attributes) {
        return provider.accept(new AuthenticationProvider.Visitor<>() {
            @Override
            public OAuth2UserInfo visitGoogle() {
                return new GoogleOAuth2UserInfo(attributes);
            }

            @Override
            public OAuth2UserInfo visitGithub() {
                return new GithubOAuth2UserInfo(attributes);
            }
        });
    }
}
