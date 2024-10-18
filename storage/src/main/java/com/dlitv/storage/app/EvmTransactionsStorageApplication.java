package com.dlitv.storage.app;

import com.dlitv.storage.service.BlockchainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.dlitv.storage")
public class EvmTransactionsStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvmTransactionsStorageApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BlockchainService blockchainService) {
        return args -> blockchainService.processTransactions();
    }
}
