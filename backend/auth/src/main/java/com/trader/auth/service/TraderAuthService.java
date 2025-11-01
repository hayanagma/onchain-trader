package com.trader.auth.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.client.IdentityClient;
import com.trader.auth.client.LedgerClient;
import com.trader.auth.dto.AuthResult;
import com.trader.auth.dto.player.ChallengeRequest;
import com.trader.auth.dto.player.ChallengeResponse;
import com.trader.auth.dto.player.TraderLoginRequest;
import com.trader.auth.security.WalletAuthenticationToken;
import com.trader.auth.validation.TokenValidator;
import com.trader.shared.dto.identity.trader.TraderResponse;
import com.trader.shared.dto.ledger.wallet.WalletResponse;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;

@Service
public class TraderAuthService {

    private final TraderNonceService traderNonceService;
    private final LedgerClient ledgerClient;
    private final AuthenticationManager authenticationManager;
    private final IdentityClient identityClient;
    private final TokenValidator tokenValidator;

    public TraderAuthService(TraderNonceService traderNonceService,
            LedgerClient ledgerClient,
            TokenValidator tokenValidator,
            AuthenticationManager authenticationManager,
            IdentityClient identityClient) {
        this.traderNonceService = traderNonceService;
        this.ledgerClient = ledgerClient;
        this.authenticationManager = authenticationManager;
        this.identityClient = identityClient;
        this.tokenValidator = tokenValidator;
    }

    public ChallengeResponse createChallenge(ChallengeRequest request) {
        WalletValidationResponse validated = ledgerClient.validateWallet(request.getWalletAddress(),
                request.getNetwork());

        String nonce = traderNonceService.createNonce(
                validated.getAddress(),
                validated.getNetwork());

        return new ChallengeResponse(nonce);
    }

    @Transactional
    public AuthResult login(TraderLoginRequest request) {
        WalletValidationResponse validated = ledgerClient.validateWalletSignature(
                request.getWalletAddress(),
                request.getNetwork(),
                request.getNonce(),
                request.getSignature());

        traderNonceService.consumeNonce(request.getNonce(), validated.getAddress(), validated.getNetwork());

        TraderResponse trader = resolveTraderAndWallet(validated);

        Authentication authentication = authenticationManager.authenticate(
                new WalletAuthenticationToken(validated.getAddress(), validated.getNetwork()));

        Long traderId = Long.valueOf(authentication.getPrincipal().toString());

        if (trader.isBanned()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    trader.getBannedReason() != null ? trader.getBannedReason() : "Trader is banned");
        }

        int tokenVersion = identityClient.getTokenVersion("ROLE_TRADER", traderId.toString());
        return tokenValidator.issueTokens(traderId.toString(), "ROLE_TRADER", tokenVersion);
    }

    public AuthResult refresh(String refreshToken) {
        Long traderId = tokenValidator.validateAndGetTraderId(refreshToken, identityClient);
        int tokenVersion = identityClient.getTokenVersion("ROLE_TRADER", traderId.toString());
        return tokenValidator.issueTokens(traderId.toString(), "ROLE_TRADER", tokenVersion);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null) {
            return;
        }
        try {
            Long traderId = tokenValidator.validateAndGetTraderId(refreshToken, identityClient);
            identityClient.bumpTokenVersion("ROLE_TRADER", traderId.toString());
        } catch (JwtException e) {
            // ignore invalid token
        }
    }

    private TraderResponse resolveTraderAndWallet(WalletValidationResponse validated) {
        Optional<WalletResponse> walletOpt = ledgerClient.findByAddressAndNetwork(
                validated.getAddress(),
                validated.getNetwork());

        if (walletOpt.isPresent()) {
            WalletResponse wallet = walletOpt.get();

            if (!wallet.isActive() && wallet.getTraderId() != null) {
                ledgerClient.reactivateWallet(wallet.getId());
            }

            return identityClient.getTrader(wallet.getTraderId());
        } else {
            TraderResponse trader = identityClient.createTrader();
            ledgerClient.ensureWallet(trader.getId(), validated.getAddress(), validated.getNetwork());

           
            ledgerClient.initializeNetworkAccounts(trader.getId());

            return trader;
        }
    }
}