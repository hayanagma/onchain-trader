package com.trader.auth.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WalletAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal; // can be walletAddress (unauthenticated) or playerId (authenticated)
    private final String network;
    private Object credentials; // can be signature later if you add challenge/response

    // Constructor for login attempts (unauthenticated)
    public WalletAuthenticationToken(String walletAddress, String network) {
        super(null);
        this.principal = walletAddress;
        this.network = network;
        this.credentials = null;
        setAuthenticated(false);
    }

    // Constructor for authenticated tokens
    public WalletAuthenticationToken(Object principal, String network,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal; // usually playerId
        this.network = network;
        this.credentials = null;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getNetwork() {
        return network;
    }
}