package com.trader.auth.security;

import org.springframework.stereotype.Component;

import com.trader.auth.client.LedgerClient;
import com.trader.shared.enums.NetworkType;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Component
public class TraderAuthProvider implements AuthenticationProvider {

    private final LedgerClient ledgerClient;

    public TraderAuthProvider(LedgerClient ledgerClient) {
        this.ledgerClient = ledgerClient;
    }

    @Override
public Authentication authenticate(Authentication authentication) {
    WalletAuthenticationToken token = (WalletAuthenticationToken) authentication;
    String walletAddress = (String) token.getPrincipal();
    NetworkType network = token.getNetwork(); // now enum

    return ledgerClient.findByAddressAndNetwork(walletAddress, network)
            .map(wallet -> new WalletAuthenticationToken(
                    wallet.getTraderId(),
                    network,
                    List.of(new SimpleGrantedAuthority("ROLE_TRADER"))))
            .orElseThrow(() -> new BadCredentialsException("Invalid wallet"));
}
    @Override
    public boolean supports(Class<?> authentication) {
        return WalletAuthenticationToken.class.isAssignableFrom(authentication);
    }
}