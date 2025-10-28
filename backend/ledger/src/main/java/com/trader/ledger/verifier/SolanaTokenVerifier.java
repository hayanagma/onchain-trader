package com.trader.ledger.verifier;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.trader.ledger.dto.TokenMetadata;

@Service
public class SolanaTokenVerifier implements BlockchainVerifier {

    private final RestTemplate restTemplate;
    private final String heliusRpcUrl;
    private final String tokenListUrl;

    public SolanaTokenVerifier(
            @Value("${solana.helius.api.url}") String heliusRpcUrl,
            @Value("${solana.tokenlist.url}") String tokenListUrl) {
        this.heliusRpcUrl = heliusRpcUrl;
        this.tokenListUrl = tokenListUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TokenMetadata verify(String mintAddress) {
        int decimals = fetchDecimals(mintAddress);
        Map<String, String> metadata = fetchOffchainMetadata(mintAddress);
        return new TokenMetadata(
                metadata.getOrDefault("symbol", "UNKNOWN"),
                metadata.getOrDefault("name", "UNKNOWN"),
                decimals);
    }

    private int fetchDecimals(String mintAddress) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", 1,
                "method", "getAccountInfo",
                "params", new Object[] { mintAddress, Map.of("encoding", "jsonParsed") });

        ResponseEntity<Map<String, Object>> resp = restTemplate.exchange(
                heliusRpcUrl,
                HttpMethod.POST,
                new HttpEntity<>(payload, headers),
                new ParameterizedTypeReference<>() {
                });

        if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
            Object resultObj = resp.getBody().get("result");
            if (resultObj instanceof Map<?, ?> result) {
                Object valueObj = result.get("value");
                if (valueObj instanceof Map<?, ?> value) {
                    Object dataObj = value.get("data");
                    if (dataObj instanceof Map<?, ?> data) {
                        Object parsedObj = data.get("parsed");
                        if (parsedObj instanceof Map<?, ?> parsed
                                && "mint".equals(parsed.get("type"))) {
                            Map<?, ?> info = (Map<?, ?>) parsed.get("info");
                            Object decimalsObj = info.get("decimals");
                            if (decimalsObj instanceof Number n) {
                                return n.intValue();
                            }
                        }
                    }
                }
            }
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Unknown or unsupported Solana mint: " + mintAddress);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> fetchOffchainMetadata(String mintAddress) {
        try {
            Map<String, Object> root = restTemplate.getForObject(tokenListUrl, Map.class);
            List<Map<String, Object>> tokens = (List<Map<String, Object>>) root.get("tokens");
            for (Map<String, Object> token : tokens) {
                if (mintAddress.equals(token.get("address"))) {
                    return Map.of(
                            "symbol", (String) token.get("symbol"),
                            "name", (String) token.get("name"));
                }
            }
        } catch (Exception ignored) {
        }
        return Map.of("symbol", "UNKNOWN", "name", "UNKNOWN");
    }
}