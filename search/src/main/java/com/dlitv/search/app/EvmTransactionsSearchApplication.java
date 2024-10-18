package com.dlitv.search.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dlitv.search")
public class EvmTransactionsSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvmTransactionsSearchApplication.class, args);
    }

}
