package com.dlitv.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "arbitrum_transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbitrumTransactionEntity {

    @Id
    @Column(name = "transaction_hash", nullable = false, unique = true)
    private String hash;

    @Column(name = "from_address", nullable = false)
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "nonce", nullable = false)
    private BigInteger nonce;

    @Column(name = "value", nullable = false)
    private BigInteger value;

    @Column(name = "gas_price", nullable = false)
    private BigInteger gasPrice;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "block_number")
    private BigInteger blockNumber;

    @Column(name = "transaction_index")
    private BigInteger transactionIndex;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

}
