package com.trader.ledger.verifier.token;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.dto.TokenMetadata;

@Service
public class EthereumTokenVerifier implements BlockchainVerifier {

    private final RestTemplate restTemplate;
    private final String infuraRpcUrl;

    public EthereumTokenVerifier(@Value("${eth.infura.rpc.url}") String infuraRpcUrl) {
        this.infuraRpcUrl = infuraRpcUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TokenMetadata verify(String contractAddress) {
        try {
            String symbol = ethCall(contractAddress, "0x95d89b41");
            String name = ethCall(contractAddress, "0x06fdde03"); 
            String decimalsHex = ethCall(contractAddress, "0x313ce567"); 
            int decimals = parseDecimals(decimalsHex);
            if (symbol.isEmpty() || name.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported or invalid ERC20 token");
            }
            return new TokenMetadata(name, symbol, decimals);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown or unsupported ETH token");
        }
    }

    private String ethCall(String contractAddress, String data) {
        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", 1,
                "method", "eth_call",
                "params", List.of(
                        Map.of("to", contractAddress, "data", data),
                        "latest"));

        ResponseEntity<Map<String, Object>> resp = restTemplate.exchange(
                infuraRpcUrl,
                HttpMethod.POST,
                new HttpEntity<>(payload),
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        if (resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ethereum RPC error");
        }

        Object resultObj = resp.getBody().get("result");
        if (resultObj instanceof String hex && hex.length() > 2) {
            return decodeERC20String(hex);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty or invalid result from Ethereum RPC");
    }

    private String decodeERC20String(String hex) {
        hex = hex.replace("0x", "");
        if (hex.isEmpty())
            return "";
        try {
            byte[] bytes = new byte[hex.length() / 2];
            for (int i = 0; i < hex.length(); i += 2) {
                bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            }
            String decoded = new String(bytes).trim();
            return decoded.replaceAll("\\p{C}", "");
        } catch (Exception e) {
            return "";
        }
    }

    private int parseDecimals(String hex) {
        try {
            String cleaned = hex.replace("0x", "");
            if (cleaned.isEmpty())
                return 18;
            return Integer.parseInt(cleaned, 16);
        } catch (Exception e) {
            return 18;
        }
    }
}