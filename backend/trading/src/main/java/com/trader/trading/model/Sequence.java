package com.trader.trading.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sequences")
public class Sequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String network;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private boolean autoExit;

    @Column(precision = 38, scale = 18)
    private BigDecimal exitLeverage;

    @OneToMany(mappedBy = "sequence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAutoExit() {
        return autoExit;
    }

    public void setAutoExit(boolean autoExit) {
        this.autoExit = autoExit;
    }

    public BigDecimal getExitLeverage() {
        return exitLeverage;
    }

    public void setExitLeverage(BigDecimal exitLeverage) {
        this.exitLeverage = exitLeverage;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
