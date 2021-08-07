package com.victorfisyuk.spring.security.oauth2.logindemo.model.principal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.victorfisyuk.spring.security.oauth2.logindemo.model.User;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * A representation of a user {@code Principal} that is registered with an OAuth 2.0 Provider
 * and that has a user in the local database linked with that OAuth 2.0 user.
 * <p/>
 * The {@code username} and {@code authorities} of the {@code Principal} are set based on the user
 * from the local database.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalUserLinkedOAuth2User extends DefaultOAuth2User {
    private static final long serialVersionUID = 496704212840114747L;

    private final String username;

    @JsonCreator
    public LocalUserLinkedOAuth2User(
            @JsonProperty("username") String username,
            @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
            @JsonProperty("attributes") Map<String, Object> attributes,
            @JsonProperty("nameAttributeKey") String nameAttributeKey
    ) {
        super(authorities, attributes, nameAttributeKey);
        this.username = username;
    }

    /**
     * Constructs a {@code LocalUserLinkedOAuth2User} based on the local database user and the user that is registered
     * with an OAuth 2.0 Provider.
     *
     * @param user the local database user
     * @param oauth2User the OAuth 2.0 user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from {@link #getAttributes()}.
     *                         Despite being required this key will not be used. Instead, this {@code Principal}
     *                         will have the name based on the local database user name.
     * @return the created {@code LocalUserLinkedOAuth2User}
     */
    public static LocalUserLinkedOAuth2User of(User user, OAuth2User oauth2User, String nameAttributeKey) {
        return new LocalUserLinkedOAuth2User(
                user.getUsername(),
                user.getAuthorities(),
                oauth2User.getAttributes(),
                nameAttributeKey
        );
    }

    @Override
    public String getName() {
        return username;
    }
}
