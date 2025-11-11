package com.trader.trading.repository;

import com.trader.trading.model.ExecutionWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExecutionWalletRepository extends JpaRepository<ExecutionWallet, Long> {
    List<ExecutionWallet> findByExecutionId(Long executionId);
}
