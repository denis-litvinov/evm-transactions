package com.dlitv.storage.service.impl;

import com.dlitv.storage.ArbitrumTransactionEvent;
import com.dlitv.storage.service.BlockchainService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import java.io.IOException;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockchainServiceImpl implements BlockchainService {

    private final Web3j web3j;

    private final RedisBlockStateServiceImpl redisBlockStateServiceImpl;

    private final KafkaTemplate<String, ArbitrumTransactionEvent> kafkaTemplate;

    private volatile boolean isShuttingDown = false;

    public void processTransactions() {
        BigInteger lastProcessedBlock = this.redisBlockStateServiceImpl.getLastProcessedBlock();

        if (lastProcessedBlock == null) {
            lastProcessedBlock = getLatestBlockFromArbitrum();
        }
        log.info("Processing blocks starting from block: {}", lastProcessedBlock);

        web3j.replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(lastProcessedBlock), false)
                .subscribe(
                        block -> {
                            if (!isShuttingDown) {
                                EthBlock.Block ethBlock = block.getBlock();
                                log.info("Processing block: {}", ethBlock.getNumber());

                                this.processBlock(ethBlock);
                            }
                        },
                        error -> log.error("Error occured while processing block: {}", error.getMessage())
                );
    }

    private void processBlock(EthBlock.Block ethBlock) {
        web3j.ethGetBlockByHash(ethBlock.getHash(), true).sendAsync().thenAccept(fullBlockResponse -> {
            EthBlock.Block fullBlock = fullBlockResponse.getBlock();

            fullBlock.getTransactions().forEach(txResult -> {

                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) txResult.get();

                ArbitrumTransactionEvent arbitrumTransactionEvent = buildArbitrumTransactionEvent(transaction);

                log.info("Sending to kafka broker transaction: {}", transaction.getHash());

                // TODO: topic name should be extracted into application.properties
                this.kafkaTemplate.send("arbitrum-transaction-event", arbitrumTransactionEvent);
            });

            redisBlockStateServiceImpl.saveLastProcessedBlock(fullBlock.getNumber());
        });
    }

    private BigInteger getLatestBlockFromArbitrum() {
        BigInteger lastProcessedBlock;
        try {
            EthBlockNumber latestBlockResponse = web3j.ethBlockNumber().send();
            lastProcessedBlock = latestBlockResponse.getBlockNumber();
            log.info("No last processed block found. Starting from the latest block: {}", lastProcessedBlock);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch the latest block from arbitrum", e);
        }
        return lastProcessedBlock;
    }

    @PreDestroy
    public void onShutdown() {
        log.info("Shutting down blockchain service");
        isShuttingDown = true;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Last processed block is saved");
    }

    private ArbitrumTransactionEvent buildArbitrumTransactionEvent(EthBlock.TransactionObject transaction) {
        return ArbitrumTransactionEvent.newBuilder()
                .setHash(transaction.getHash())
                .setBlockNumber(transaction.getBlockNumber().longValue())
                .setBlockHash(transaction.getBlockHash())
                .setFromAddress(transaction.getFrom())
                .setToAddress(transaction.getTo())
                .setGasPrice(transaction.getGasPrice().toString())
                .setNonce(transaction.getNonce().longValue())
                .setTransactionIndex(transaction.getTransactionIndex().longValue())
                .setValue(transaction.getValue().toString())
                .build();
    }

}
