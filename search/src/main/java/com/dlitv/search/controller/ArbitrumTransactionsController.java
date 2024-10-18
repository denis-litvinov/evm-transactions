package com.dlitv.search.controller;

import com.dlitv.search.entity.ArbitrumTransactionEntity;
import com.dlitv.search.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class ArbitrumTransactionsController {

    private final BlockchainService blockchainService;

    @GetMapping("/hash/{hash}")
    public ResponseEntity<ArbitrumTransactionEntity> getArbitrumTransactionByHash(@PathVariable String hash) {
        return ResponseEntity.ok(this.blockchainService.getTransactionByHash(hash));
    }

    @GetMapping("/from/{fromAddress}")
    public ResponseEntity<List<ArbitrumTransactionEntity>> getArbitrumTransactionsByFromAddress(@PathVariable String fromAddress) {
        return ResponseEntity.ok(this.blockchainService.getTransactionsByFromAddress(fromAddress));
    }

    @GetMapping("/to/{toAddress}")
    public ResponseEntity<List<ArbitrumTransactionEntity>> getArbitrumTransactionByToAddress(@PathVariable String toAddress) {
        return ResponseEntity.ok(this.blockchainService.getTransactionsByToAddress(toAddress));
    }

    @GetMapping("/block/{blockNumber}")
    public ResponseEntity<List<ArbitrumTransactionEntity>> getArbitrumTransactionsByBlockNumber(@PathVariable String blockNumber) {
        return ResponseEntity.ok(this.blockchainService.getTransactionsByBlockNumber(blockNumber));
    }
}
