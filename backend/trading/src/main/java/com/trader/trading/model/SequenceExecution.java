package com.trader.trading.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sequence_executions")
public class SequenceExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id", nullable = false)
    private Sequence sequence;

    @OneToMany(mappedBy = "execution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExecutionWallet> wallets;

    @Column(nullable = false)
    private boolean sellAll;

    @Column(nullable = false)
    private Long executedAt;

    @Column(length = 128)
    private String txHash;

    public Long getId() {
        return id;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public List<ExecutionWallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<ExecutionWallet> wallets) {
        this.wallets = wallets;
    }

    public boolean isSellAll() {
        return sellAll;
    }

    public void setSellAll(boolean sellAll) {
        this.sellAll = sellAll;
    }

    public Long getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Long executedAt) {
        this.executedAt = executedAt;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
}
