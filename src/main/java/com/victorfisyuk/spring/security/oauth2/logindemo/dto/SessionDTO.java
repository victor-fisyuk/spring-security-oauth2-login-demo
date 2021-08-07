package com.victorfisyuk.spring.security.oauth2.logindemo.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class SessionDTO {
    String id;
    Instant creationTime;
    long maxInactiveIntervalMinutes;
    boolean expired;
}
