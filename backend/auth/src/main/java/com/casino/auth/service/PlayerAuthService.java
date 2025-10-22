package com.casino.auth.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import com.casino.auth.client.IdentityClient;
import com.casino.auth.client.LedgerClient;
import com.casino.auth.dto.AuthResult;
import com.casino.auth.dto.player.ChallengeRequest;
import com.casino.auth.dto.player.ChallengeResponse;
import com.casino.auth.dto.player.PlayerLoginRequest;
import com.casino.auth.dto.player.PlayerResponse;
import com.casino.auth.dto.wallet.WalletResponse;
import com.casino.auth.dto.wallet.WalletValidationResponse;
import com.casino.auth.security.WalletAuthenticationToken;
import com.casino.auth.validation.TokenValidator;

import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;

@Service
public class PlayerAuthService {

    private final PlayerNonceService playerNonceService;
    private final LedgerClient walletClient;
    private final AuthenticationManager authenticationManager;
    private final IdentityClient identityClient;
    private final TokenValidator tokenValidator;

    public PlayerAuthService(PlayerNonceService playerNonceService, LedgerClient walletClient,
            TokenValidator tokenValidator,
            AuthenticationManager authenticationManager, IdentityClient identityClient,
            TokenValidator tokenValidator2) {
        this.playerNonceService = playerNonceService;
        this.walletClient = walletClient;
        this.authenticationManager = authenticationManager;
        this.identityClient = identityClient;
        this.tokenValidator = tokenValidator2;

    }

    public ChallengeResponse createChallenge(ChallengeRequest request) {
        WalletValidationResponse validated = walletClient.validateWallet(request.getWalletAddress(),
                request.getNetwork());

        String nonce = playerNonceService.createNonce(
                validated.getAddress(),
                validated.getNetwork());

        return new ChallengeResponse(nonce);
    }

    @Transactional
    public AuthResult login(PlayerLoginRequest request) {
        WalletValidationResponse validated = walletClient.validateWalletSignature(
                request.getWalletAddress(),
                request.getNetwork(),
                request.getNonce(),
                request.getSignature());

        playerNonceService.consumeNonce(request.getNonce(), validated.getAddress(), validated.getNetwork());

        Optional<WalletResponse> walletOpt = walletClient.findByAddressAndNetwork(validated.getAddress(),
                validated.getNetwork());

        PlayerResponse player;
        if (walletOpt.isPresent()) {
            player = identityClient.getPlayer(walletOpt.get().getPlayerId());
        } else {
            player = identityClient.createPlayer();
            walletClient.ensureWallet(player.getId(), validated.getAddress(), validated.getNetwork());
        }

        Authentication authentication = authenticationManager.authenticate(
                new WalletAuthenticationToken(validated.getAddress(), validated.getNetwork()));

        Long playerId = Long.valueOf(authentication.getPrincipal().toString());

        if (player.isBanned()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    player.getBannedReason() != null ? player.getBannedReason() : "Player is banned");
        }

        int tokenVersion = identityClient.getTokenVersion("ROLE_PLAYER", playerId.toString());
        return tokenValidator.issueTokens(playerId.toString(), "ROLE_PLAYER", tokenVersion);
    }

    public AuthResult refresh(String refreshToken) {
        Long playerId = tokenValidator.validateAndGetPlayerId(refreshToken, identityClient);
        int tokenVersion = identityClient.getTokenVersion("ROLE_PLAYER", playerId.toString());
        return tokenValidator.issueTokens(playerId.toString(), "ROLE_PLAYER", tokenVersion);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null) {
            return;
        }
        try {
            Long playerId = tokenValidator.validateAndGetPlayerId(refreshToken, identityClient);
            identityClient.bumpTokenVersion("ROLE_PLAYER", playerId.toString());
        } catch (JwtException e) {
            // ignore invalid token
        }
    }
}
