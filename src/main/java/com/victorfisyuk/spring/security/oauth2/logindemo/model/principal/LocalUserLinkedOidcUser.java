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
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;

/**
 * A representation of a user {@code Principal} that is registered with an OpenID Connect 1.0 Provider
 * and that has a user in the local database linked with that OpenID Connect 1.0 user.
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
public class LocalUserLinkedOidcUser extends DefaultOidcUser {
    private static final long serialVersionUID = 2445253601971751672L;

    private final String username;

    @JsonCreator
    public LocalUserLinkedOidcUser(
            @JsonProperty("username") String username,
            @JsonProperty("authorities") Collection<GrantedAuthority> authorities,
            @JsonProperty("idToken") OidcIdToken idToken,
            @JsonProperty("userInfo") OidcUserInfo userInfo
    ) {
        super(authorities, idToken, userInfo);
        this.username = username;
    }

    /**
     * Constructs a {@code LocalUserLinkedOidcUser} based on the local database user and the user that is registered
     * with an OpenID Connect 1.0 Provider.
     *
     * @param user the local database user
     * @param oidcUser the OpenID Connect 1.0 user
     * @return the created {@code LocalUserLinkedOidcUser}
     */
    public static LocalUserLinkedOidcUser of(User user, OidcUser oidcUser) {
        return new LocalUserLinkedOidcUser(
                user.getUsername(),
                user.getAuthorities(),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
        );
    }

    @Override
    public String getName() {
        return username;
    }
}
