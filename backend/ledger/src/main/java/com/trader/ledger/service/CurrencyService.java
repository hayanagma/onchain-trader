package com.trader.ledger.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.dto.TokenMetadata;
import com.trader.ledger.model.Currency;
import com.trader.ledger.model.PlayerCurrency;
import com.trader.ledger.repository.CurrencyRepository;
import com.trader.ledger.repository.PlayerCurrencyRepository;
import com.trader.ledger.verifier.BlockchainVerifierFactory;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.NetworkType;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final BlockchainVerifierFactory blockchainVerifierFactory;
    private final PlayerCurrencyRepository playerCurrencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository, BlockchainVerifierFactory blockchainVerifierFactory,
            PlayerCurrencyRepository playerCurrencyRepository) {
        this.currencyRepository = currencyRepository;
        this.blockchainVerifierFactory = blockchainVerifierFactory;
        this.playerCurrencyRepository = playerCurrencyRepository;
    }

    public void createDefaultCurrencies() {
        upsertNative("TRX", "TRON", NetworkType.TRON, 6);
        upsertNative("ETH", "Ether", NetworkType.ETHEREUM, 18);
        upsertNative("SOL", "Solana", NetworkType.SOLANA, 9);
        upsertNative("BTC", "Bitcoin", NetworkType.BITCOIN, 8);
    }

    private void upsertNative(String code, String name, NetworkType network, int decimals) {
        if (currencyRepository.findByCodeAndNetwork(code, network).isEmpty()) {
            Currency currency = new Currency();
            currency.setCode(code);
            currency.setName(name);
            currency.setNetwork(network);
            currency.setDecimals(decimals);
            currency.setKind(CurrencyKind.NATIVE);
            currency.setContractAddress(null);
            currencyRepository.save(currency);
        }
    }

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currency -> new CurrencyResponse(
                        currency.getCode(),
                        currency.getName(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }

    public List<CurrencyResponse> getVisibleCurrencies(Long playerId, NetworkType network) {
        return currencyRepository.findVisibleByPlayerAndNetwork(playerId, network).stream()
                .map(currency -> new CurrencyResponse(
                        currency.getCode(),
                        currency.getName(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }

    public void createCurrency(Long playerId, CurrencyAddRequest request) {
        NetworkType network = request.getNetwork();
        String contractAddress = request.getContractAddress();

        Currency currency = currencyRepository
                .findByContractAddressAndNetwork(contractAddress, network)
                .orElseGet(() -> {
                    TokenMetadata m = blockchainVerifierFactory.getVerifier(network).verify(contractAddress);
                    Currency c = new Currency();
                    c.setCode(m.getSymbol());
                    c.setName(m.getName() != null && !m.getName().isBlank() ? m.getName() : m.getSymbol());
                    c.setNetwork(network);
                    c.setDecimals(m.getDecimals());
                    c.setKind(CurrencyKind.TOKEN);
                    c.setContractAddress(contractAddress);
                    return currencyRepository.save(c);
                });

        if (playerCurrencyRepository.existsByPlayerIdAndCurrency_Id(playerId, currency.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Player already added this token");
        }

        PlayerCurrency playerCurrency = new PlayerCurrency();
        playerCurrency.setPlayerId(playerId);
        playerCurrency.setCurrency(currency);
        playerCurrencyRepository.save(playerCurrency);
    }

}
