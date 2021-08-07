package com.victorfisyuk.spring.security.oauth2.logindemo.converter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GrantedAuthorityAttributeConverter implements AttributeConverter<GrantedAuthority, String> {
    @Override
    public String convertToDatabaseColumn(GrantedAuthority attribute) {
        return attribute != null ? attribute.getAuthority() : null;
    }

    @Override
    public GrantedAuthority convertToEntityAttribute(String dbData) {
        return dbData != null ? new SimpleGrantedAuthority(dbData) : null;
    }
}
