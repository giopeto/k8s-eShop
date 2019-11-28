package com.files.jms.consumer;

import com.files.common.JMSConstants;
import com.files.common.JmsPayload;
import com.files.v1.files.service.FilesService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JmsConsumer {

    @NonNull
    private final FilesService filesService;

    @JmsListener(destination = JMSConstants.DELETE_FILES_BY_DOMAIN_ID)
    public void deleteFilesByDomainId(JmsPayload jmsPayload) {
        log.info("[JmsConsumer] Receive message from: {} with payload: {}", JMSConstants.DELETE_FILES_BY_DOMAIN_ID, jmsPayload.toString());
        log.info("[JmsConsumer] Receive message from: {} with payload: {}", JMSConstants.DELETE_FILES_BY_DOMAIN_ID);
        filesService.deleteByDomainId(jmsPayload.getPayload());
    }
}
