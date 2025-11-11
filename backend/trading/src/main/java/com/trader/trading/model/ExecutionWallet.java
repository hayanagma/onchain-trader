package com.trader.trading.model;

import jakarta.persistence.*;

@Entity
@Table(name = "execution_wallets")
public class ExecutionWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_id", nullable = false)
    private SequenceExecution execution;

    @Column(nullable = false, length = 64)
    private String walletAddress;

    public Long getId() {
        return id;
    }

    public SequenceExecution getExecution() {
        return execution;
    }

    public void setExecution(SequenceExecution execution) {
        this.execution = execution;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
