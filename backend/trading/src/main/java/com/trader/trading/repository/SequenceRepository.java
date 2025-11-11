package com.trader.trading.repository;

import com.trader.trading.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {
    List<Sequence> findByNetwork(String network);
}
