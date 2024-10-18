package com.dlitv.storage.service.impl;

import com.dlitv.storage.ArbitrumTransactionEvent;
import com.dlitv.storage.entity.ArbitrumTransactionEntity;
import com.dlitv.storage.repository.BlockchainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumerServiceImpl {

    @Value("${spring.kafka.consumer.properties.batch-size}")
    private int batchSize;

    private final BlockchainRepository blockchainRepository;

    private final List<ArbitrumTransactionEvent> arbitrumTransactionsBuffer = new ArrayList<>();

    @KafkaListener(topics = "arbitrum-transaction-event", groupId = "arbitrum-transaction-group")
    public void consumeArbitrumTransactions(ArbitrumTransactionEvent transactionEvent, Acknowledgment ack) {
        synchronized (arbitrumTransactionsBuffer) {
            log.info("Received transaction: {}", transactionEvent.getHash());
            arbitrumTransactionsBuffer.add(transactionEvent);

            if (arbitrumTransactionsBuffer.size() >= batchSize) {
                saveTransactionsToDataBase(new ArrayList<>(arbitrumTransactionsBuffer));
                arbitrumTransactionsBuffer.clear();
                ack.acknowledge();
            }
        }
    }

    private void saveTransactionsToDataBase(List<ArbitrumTransactionEvent> arbitrumTransactionsBuffer) {
        List<ArbitrumTransactionEntity> entities = arbitrumTransactionsBuffer.stream()
                .map(this::buildArbitrumTransactionEntity)
                .toList();

        log.info("Saving [{}] transactions to database", entities.size());

        this.blockchainRepository.saveAll(entities);
    }

    private ArbitrumTransactionEntity buildArbitrumTransactionEntity(ArbitrumTransactionEvent event) {
        return ArbitrumTransactionEntity.builder()
                .hash(event.getHash())
                .fromAddress(event.getFromAddress())
                .toAddress(event.getToAddress())
                .nonce(BigInteger.valueOf(event.getNonce()))
                .gasPrice(new BigInteger(event.getGasPrice()))
                .value(new BigInteger(event.getValue()))
                .blockHash(event.getBlockHash())
                .blockNumber(BigInteger.valueOf(event.getBlockNumber()))
                .transactionIndex(BigInteger.valueOf(event.getTransactionIndex()))
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
