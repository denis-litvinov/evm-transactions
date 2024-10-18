package com.dlitv.storage.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

@Configuration
public class Web3jConfiguration {

    @Value("${websocket.url}")
    String webSocketUrl;

    @Bean
    public Web3j web3j() {
        return Web3j.build(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        WebSocketService webSocketService = new WebSocketService(this.webSocketUrl, true);
        try {
            webSocketService.connect();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        return webSocketService;
    }
}
