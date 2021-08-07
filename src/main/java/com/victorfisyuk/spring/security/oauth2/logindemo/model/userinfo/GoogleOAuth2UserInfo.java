package com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo;

import lombok.ToString;

import java.util.Map;

/**
 * Represents information about an Google OAuth 2.0 user.
 */
@ToString
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private final String id;
    private final String pictureUrl;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        id = (String) attributes.get("sub");
        pictureUrl = (String) attributes.get("picture");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPictureUrl() {
        return pictureUrl;
    }
}
