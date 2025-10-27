package com.trader.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trader.ledger.model.Currency;
import com.trader.shared.enums.NetworkType;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByNetwork(NetworkType network);

    Optional<Currency> findByCodeAndNetwork(String code, NetworkType network);

    Optional<Currency> findByContractAddressAndNetwork(String contractAddress, NetworkType network);

    @Query("""
              select c
              from Currency c
              where c.network = :network
                and (
                  c.kind = com.trader.shared.enums.CurrencyKind.NATIVE
                  or exists (
                    select 1 from PlayerCurrency pc
                    where pc.currency = c and pc.playerId = :playerId
                  )
                )
              order by c.code
            """)
    List<Currency> findVisibleByPlayerAndNetwork(Long playerId, NetworkType network);
}
