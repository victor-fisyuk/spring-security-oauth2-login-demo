package com.victorfisyuk.spring.security.oauth2.logindemo.model.userinfo;

import lombok.ToString;

import java.util.Map;

/**
 * Represents information about an GitHub OAuth 2.0 user.
 */
@ToString
public class GithubOAuth2UserInfo implements OAuth2UserInfo {
    private final String id;
    private final String pictureUrl;

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        id = String.valueOf((int) attributes.get("id"));
        pictureUrl = (String) attributes.get("avatar_url");
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
