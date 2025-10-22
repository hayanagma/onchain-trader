package com.trader.auth.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.client.IdentityClient;
import com.trader.shared.dto.identity.admin.AdminResponse;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final IdentityClient identityAdminClient;

    public AdminUserDetailsService(IdentityClient identityAdminClient) {
        this.identityAdminClient = identityAdminClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminResponse admin = identityAdminClient.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // must be encoded in DB
                .roles("ADMIN")
                .build();
    }
}
