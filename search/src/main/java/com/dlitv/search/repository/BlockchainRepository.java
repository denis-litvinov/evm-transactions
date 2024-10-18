package com.dlitv.search.repository;

import com.dlitv.search.entity.ArbitrumTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface BlockchainRepository extends JpaRepository<ArbitrumTransactionEntity, String> {

    ArbitrumTransactionEntity findArbitrumTransactionEntityByHash(String hash);

    List<ArbitrumTransactionEntity> findArbitrumTransactionEntitiesByFromAddress(String fromAddress);

    List<ArbitrumTransactionEntity> findArbitrumTransactionEntitiesByToAddress(String toAddress);

    List<ArbitrumTransactionEntity> findArbitrumTransactionEntitiesByBlockNumber(BigInteger blockNumber);
}
