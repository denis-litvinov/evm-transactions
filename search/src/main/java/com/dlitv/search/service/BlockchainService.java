package com.dlitv.search.service;

import com.dlitv.search.entity.ArbitrumTransactionEntity;

import java.util.List;

public interface BlockchainService {

    ArbitrumTransactionEntity getTransactionByHash(String hash);

    List<ArbitrumTransactionEntity> getTransactionsByFromAddress(String fromAddress);

    List<ArbitrumTransactionEntity> getTransactionsByToAddress(String toAddress);

    List<ArbitrumTransactionEntity> getTransactionsByBlockNumber(String blockNumber);


}
