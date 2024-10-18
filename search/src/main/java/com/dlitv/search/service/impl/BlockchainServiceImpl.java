package com.dlitv.search.service.impl;

import com.dlitv.search.entity.ArbitrumTransactionEntity;
import com.dlitv.search.repository.BlockchainRepository;
import com.dlitv.search.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final BlockchainRepository blockchainRepository;

    @Override
    public ArbitrumTransactionEntity getTransactionByHash(String hash) {
        return this.blockchainRepository.findArbitrumTransactionEntityByHash(hash);
    }

    @Override
    public List<ArbitrumTransactionEntity> getTransactionsByFromAddress(String fromAddress) {
        return this.blockchainRepository.findArbitrumTransactionEntitiesByFromAddress(fromAddress);
    }

    @Override
    public List<ArbitrumTransactionEntity> getTransactionsByToAddress(String toAddress) {
        return this.blockchainRepository.findArbitrumTransactionEntitiesByToAddress(toAddress);
    }

    @Override
    public List<ArbitrumTransactionEntity> getTransactionsByBlockNumber(String blockNumber) {
        return this.blockchainRepository.findArbitrumTransactionEntitiesByBlockNumber(new BigInteger(blockNumber));
    }
}
