package com.victorfisyuk.spring.security.oauth2.logindemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victorfisyuk.spring.security.oauth2.logindemo.converter.GrantedAuthorityAttributeConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "providerId"}))
@Getter
@Setter
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -5024984546881439203L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    private boolean enabled;

    @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "authority")
    @Convert(converter = GrantedAuthorityAttributeConverter.class)
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider provider;

    private String providerId;

    private String pictureUrl;
}
