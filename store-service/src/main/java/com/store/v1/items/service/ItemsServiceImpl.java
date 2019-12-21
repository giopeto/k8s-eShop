package com.store.v1.items.service;

import com.store.common.CookieParser;
import com.store.common.JMSConstants;
import com.store.common.JmsPayload;
import com.store.jms.producer.JmsProducer;
import com.store.v1.items.domain.Items;
import com.store.v1.items.domain.ItemsDto;
import com.store.v1.items.domain.ItemsList;
import com.store.v1.items.repository.ItemsRepository;
import com.store.v1.remote.call.files.FilesClient;
import com.store.v1.remote.call.files.domain.FilesToDomainMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.store.common.StoreConstants.JSESSIONID;
import static java.util.Collections.emptyList;

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
    public List<ItemsDto> findByGroupId(String groupId) {
        List<Items> items = itemsRepository.findByGroupId(groupId);

        List<FilesToDomainMapper> fileIds = filesClient.getByDomainIds(JSESSIONID + "=" + cookieParser.getCookie(JSESSIONID).get(),
                items.stream().map(item -> item.getId()).collect(Collectors.toList()));

        Map<String, FilesToDomainMapper> filesMap = fileIds.stream().collect(Collectors.toMap(FilesToDomainMapper::getDomainId, Function.identity()));

        return items.stream().map(item -> mapToItemsDto(item,
                filesMap.get(item.getId()) == null ? emptyList() : filesMap.get(item.getId()).getFileIds())).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        itemsRepository.deleteById(id);
        jmsProducer.send(JMSConstants.DELETE_FILES_BY_DOMAIN_ID, new JmsPayload(id).toString());
    }

    private ItemsDto mapToItemsDto(Items item, List<String> fileIdsPerItem) {
        return new ItemsDto(item.getId(),
                item.getName(),
                item.getGroupId(),
                item.getShortDescription(),
                item.getDescription(),
                item.getPrice(),
                item.isArchive(),
                fileIdsPerItem);
    }
}