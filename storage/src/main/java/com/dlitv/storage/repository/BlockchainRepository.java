package com.dlitv.storage.repository;

import com.dlitv.storage.entity.ArbitrumTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockchainRepository extends JpaRepository<ArbitrumTransactionEntity, String> {
}
