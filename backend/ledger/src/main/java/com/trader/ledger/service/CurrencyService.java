package com.trader.ledger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trader.ledger.dto.TokenMetadata;
import com.trader.ledger.model.Currency;
import com.trader.ledger.repository.CurrencyRepository;
import com.trader.ledger.validation.CurrencyValidator;
import com.trader.ledger.verifier.BlockchainVerifierFactory;
import com.trader.shared.dto.ledger.currency.CurrencyAddRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.enums.CurrencyKind;
import com.trader.shared.enums.NetworkType;

import jakarta.transaction.Transactional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final BlockchainVerifierFactory blockchainVerifierFactory;
    private final CurrencyValidator currencyValidator;
    private final WalletService walletService;
    private final TraderCurrencyService traderCurrencyService;

    public CurrencyService(CurrencyRepository currencyRepository,
            BlockchainVerifierFactory blockchainVerifierFactory,
            CurrencyValidator currencyValidator, WalletService walletService,
            TraderCurrencyService traderCurrencyService) {
        this.currencyRepository = currencyRepository;
        this.blockchainVerifierFactory = blockchainVerifierFactory;
        this.currencyValidator = currencyValidator;
        this.walletService = walletService;
        this.traderCurrencyService = traderCurrencyService;
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

    public List<CurrencyResponse> getVisibleCurrencies(Long traderId) {
        NetworkType traderNetwork = walletService.getTraderNetwork(traderId);

        return currencyRepository.findVisibleByTraderAndNetwork(traderId, traderNetwork).stream()
                .map(currency -> new CurrencyResponse(
                        currency.getCode(),
                        currency.getName(),
                        currency.getNetwork(),
                        currency.getDecimals(),
                        currency.getKind(),
                        currency.getContractAddress()))
                .toList();
    }

    @Transactional
    public void createCurrency(Long traderId, CurrencyAddRequest request) {
        NetworkType traderNetwork = walletService.getTraderNetwork(traderId);

        String contractAddress = request.getContractAddress();

        currencyValidator.validateAddress(contractAddress, traderNetwork);

        Currency currency = currencyRepository
                .findByContractAddressAndNetwork(contractAddress, traderNetwork)
                .orElseGet(() -> {
                    TokenMetadata metadata = blockchainVerifierFactory
                            .getVerifier(traderNetwork)
                            .verify(contractAddress);
                    Currency c = new Currency();
                    c.setCode(metadata.getSymbol());
                    c.setName((metadata.getName() != null && !metadata.getName().isBlank())
                            ? metadata.getName()
                            : metadata.getSymbol());
                    c.setNetwork(traderNetwork);
                    c.setDecimals(metadata.getDecimals());
                    c.setKind(CurrencyKind.TOKEN);
                    c.setContractAddress(contractAddress);
                    return currencyRepository.save(c);
                });

        traderCurrencyService.linkTraderToCurrency(traderId, currency);
    }

}
