package com.store.v1.items.service;

import com.store.common.CookieParser;
import com.store.jms.producer.JmsProducer;
import com.store.v1.items.domain.Items;
import com.store.v1.items.domain.ItemsList;
import com.store.v1.items.repository.ItemsRepository;
import com.store.v1.remote.call.files.FilesClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    @NonNull
    private final ItemsRepository itemsRepository;

    @NonNull
    private final FilesClient filesClient;

    @NonNull
    private final CookieParser cookieParser;

    @NonNull
    private final JmsProducer jmsProducer;

    @Override
    public Items save(Items item) {
        return itemsRepository.save(item);
    }

    @Override
    public ItemsList get() {
        return new ItemsList(itemsRepository.findAll());
    }

    @Override
    public Optional<Items> findById(String id) {
        return itemsRepository.findById(id);
    }

    @Override
    public ItemsList findByGroupId(String groupId) {
        return new ItemsList(itemsRepository.findByGroupId(groupId));
    }

    @Override
    public void delete(String id) {
        itemsRepository.deleteById(id);
        jmsProducer.send("test-activemq", id);
        //filesClient.deleteByDomainId(JSESSIONID + "=" + cookieParser.getCookie(JSESSIONID).get(), id);
    }
}
