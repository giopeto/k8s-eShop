package com.files.jms.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JmsConsumer {

    @JmsListener(destination = "test-activemq")
    public void receive(String message) {
        log.info("[JmsConsumer] Receive message from: test-activemq with data: {}", message);
    }
}
