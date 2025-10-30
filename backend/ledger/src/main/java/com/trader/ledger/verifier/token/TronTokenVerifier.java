package com.trader.ledger.verifier.token;

import com.trader.ledger.dto.TokenMetadata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TronTokenVerifier implements BlockchainVerifier {

    private final RestTemplate restTemplate;
    private final String trongridApiKey;
    private final String trongridApiUrl;
    private final String tronscanApiKey;
    private final String tronscanApiUrl;

    public TronTokenVerifier(
            @Value("${tron.grid.api.key}") String trongridApiKey,
            @Value("${tron.grid.api.url}") String trongridApiUrl,
            @Value("${tron.scan.api.key}") String tronscanApiKey,
            @Value("${tron.scan.api.url}") String tronscanApiUrl) {
        this.trongridApiKey = trongridApiKey;
        this.trongridApiUrl = trongridApiUrl;
        this.tronscanApiKey = tronscanApiKey;
        this.tronscanApiUrl = tronscanApiUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TokenMetadata verify(String contractAddress) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("TRON-PRO-API-KEY", trongridApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 1. Try on-chain contract call (symbol check)
        try {
            String url = trongridApiUrl + "/wallet/triggerconstantcontract";
            Map<String, Object> payload = Map.of(
                    "owner_address", "410000000000000000000000000000000000000000",
                    "contract_address", contractAddress,
                    "function_selector", "symbol()",
                    "parameter", "");

            ResponseEntity<Map<String, Object>> resp = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(payload, headers),
                    new ParameterizedTypeReference<>() {
                    });

            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                Object result = ((Map<?, ?>) resp.getBody()).get("constant_result");
                if (result instanceof List<?> list && !list.isEmpty()) {
                    String encoded = list.get(0).toString();
                    if (!encoded.isEmpty()) {
                        byte[] bytes = hexToBytes(encoded);
                        String symbol = new String(bytes).trim().replaceAll("\\p{C}", "");
                        if (!symbol.isEmpty()) {
                            return getMetadataFromTronscan(contractAddress, symbol);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }

        // 2. Fallback: query TronScan metadata
        return getMetadataFromTronscan(contractAddress, "UNKNOWN");
    }

    private TokenMetadata getMetadataFromTronscan(String contractAddress, String symbolFallback) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("TRON-PRO-API-KEY", tronscanApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String url = tronscanApiUrl + "/api/token_trc20?contract=" + contractAddress + "&showAll=1";
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object tokensObj = response.getBody().get("trc20_tokens");
                if (tokensObj instanceof List<?> tokenList && !tokenList.isEmpty()) {
                    Object first = tokenList.get(0);
                    if (first instanceof Map<?, ?> token) {
                        String symbol = token.get("symbol") != null ? token.get("symbol").toString() : symbolFallback;
                        String name = token.get("name") != null ? token.get("name").toString() : symbolFallback;
                        int decimals = token.get("decimals") instanceof Number
                                ? ((Number) token.get("decimals")).intValue()
                                : 6;
                        return new TokenMetadata(name, symbol, decimals);
                    }
                }
            }
        } catch (HttpClientErrorException ignored) {
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Unknown or unsupported TRON token: " + contractAddress);
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return result;
    }
}