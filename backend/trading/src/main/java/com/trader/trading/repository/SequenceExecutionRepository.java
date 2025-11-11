package com.trader.trading.repository;

import com.trader.trading.model.SequenceExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceExecutionRepository extends JpaRepository<SequenceExecution, Long> {
}
