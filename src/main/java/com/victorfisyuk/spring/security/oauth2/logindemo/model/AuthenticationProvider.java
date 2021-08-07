package com.victorfisyuk.spring.security.oauth2.logindemo.model;

/**
 * Represents OAuth 2.0 and OpenID Connect 1.0 providers
 * which can be used for the authentication.
 */
public enum AuthenticationProvider {
    /**
     * Google OpenID Connect
     */
    GOOGLE {
        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visitGoogle();
        }
    },

    /**
     * GitHub OAuth 2
     */
    GITHUB {
        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visitGithub();
        }
    };

    public abstract <T> T accept(Visitor<T> visitor);

    public interface Visitor<T> {
        T visitGoogle();
        T visitGithub();
    }
}
