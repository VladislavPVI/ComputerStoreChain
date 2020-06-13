package com.pvi.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}