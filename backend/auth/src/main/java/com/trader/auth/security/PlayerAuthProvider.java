package com.trader.auth.security;

import org.springframework.stereotype.Component;

import com.trader.auth.client.LedgerClient;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Component
public class PlayerAuthProvider implements AuthenticationProvider {

    private final LedgerClient walletClient;

    public PlayerAuthProvider(LedgerClient walletClient) {
        this.walletClient = walletClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        WalletAuthenticationToken token = (WalletAuthenticationToken) authentication;
        String walletAddress = (String) token.getPrincipal();
        String network = token.getNetwork();

        return walletClient.findByAddressAndNetwork(walletAddress, network)
                .map(wallet -> new WalletAuthenticationToken(
                        wallet.getPlayerId(),
                        network,
                        List.of(new SimpleGrantedAuthority("ROLE_PLAYER"))))
                .orElseThrow(() -> new BadCredentialsException("Invalid wallet"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WalletAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
