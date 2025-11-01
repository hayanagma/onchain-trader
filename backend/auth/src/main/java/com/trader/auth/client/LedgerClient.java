package com.trader.auth.client;

import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.trader.auth.util.RestTemplateUtil;
import com.trader.shared.dto.ledger.wallet.WalletEnsureRequest;
import com.trader.shared.dto.ledger.wallet.WalletResponse;
import com.trader.shared.dto.ledger.wallet.WalletSignatureValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationRequest;
import com.trader.shared.dto.ledger.wallet.WalletValidationResponse;
import com.trader.shared.enums.NetworkType;

@Service
public class LedgerClient {

    private final RestTemplate restTemplate;

    public LedgerClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .rootUri("http://ledger:8082/api/internal/ledger")
                .build();
    }

    public Optional<WalletResponse> findByAddressAndNetwork(String address, NetworkType network) {
        try {
            WalletResponse body = RestTemplateUtil.get(
                    restTemplate,
                    "/wallets?address={address}&network={network}",
                    WalletResponse.class,
                    address,
                    network);
            return Optional.ofNullable(body);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode().value() == 404) {
                return Optional.empty();
            }
            throw ex;
        }
    }

    public WalletValidationResponse validateWallet(String address, NetworkType network) {
        WalletValidationRequest req = new WalletValidationRequest(address, network);
        return RestTemplateUtil.post(
                restTemplate,
                "/wallets/validate",
                req,
                WalletValidationResponse.class);
    }

    public WalletValidationResponse validateWalletSignature(
            String address,
            NetworkType network,
            String nonce,
            String signature) {
        WalletSignatureValidationRequest req = new WalletSignatureValidationRequest(address, network, nonce, signature);

        return RestTemplateUtil.post(
                restTemplate,
                "/wallets/validate-signature",
                req,
                WalletValidationResponse.class);
    }

    public void ensureWallet(Long traderId, String address, NetworkType network) {
        RestTemplateUtil.postEntity(
                restTemplate,
                "/wallets/ensure",
                new WalletEnsureRequest(traderId, address, network),
                Void.class);
    }

    public void reactivateWallet(Long walletId) {
        RestTemplateUtil.postEntity(
                restTemplate,
                "/wallets/reactivate?walletId={walletId}",
                null,
                Void.class,
                walletId);
    }

    public void initializeNetworkAccounts(Long traderId) {
        RestTemplateUtil.postEntity(
                restTemplate,
                "/network-accounts/initialize?traderId=" + traderId,
                null,
                Void.class);
    }
}