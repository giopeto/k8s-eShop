package com.store.jms.producer;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JmsProducer {

    @NonNull
    private final JmsTemplate jmsTemplate;

    public void send(String destination, String id) {
        log.info("[JmsProducer] Send message to: {} with data: {}", destination, id);
        jmsTemplate.convertAndSend(destination, id);
    }
}
