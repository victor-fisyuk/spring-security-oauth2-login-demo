package com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo;

import org.springframework.lang.Nullable;

/**
 * Represents information about an OAuth 2.0 user.
 * <p>This information is obtaining from the OAuth 2.0 token attributes.
 */
public interface OAuth2UserInfo {
    /**
     * Returns the OAuth 2.0 user ID.
     *
     * @return the OAuth 2.0 user ID
     */
    String getId();

    /**
     * Returns the picture URL of the OAuth 2.0 user.
     *
     * @return the picture URL
     */
    @Nullable String getPictureUrl();
}
