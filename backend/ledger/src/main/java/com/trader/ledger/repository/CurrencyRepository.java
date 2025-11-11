package com.trader.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.Currency;
import com.trader.shared.enums.NetworkType;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  List<Currency> findByNetwork(NetworkType network);

  Optional<Currency> findByCodeAndNetwork(String code, NetworkType network);

  Optional<Currency> findByContractAddressAndNetwork(String contractAddress, NetworkType network);

  @Query("""
          select c
          from Currency c
          where c.kind = com.trader.shared.enums.CurrencyKind.NATIVE
             or exists (
               select 1 from TraderCurrency tc
               where tc.currency = c and tc.traderId = :traderId
             )
          order by c.code
      """)
  List<Currency> findVisibleByTrader(@Param("traderId") Long traderId);
}