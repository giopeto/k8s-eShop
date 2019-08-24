package com.store.V1.items.service;

import com.store.V1.items.domain.Items;
import com.store.V1.items.domain.ItemsList;
import com.store.V1.items.repository.ItemsRepository;
import com.store.V1.remote.call.files.FilesClient;
import com.store.common.AppUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.store.common.StoreConstants.JSESSIONID;

@Service
@AllArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    @NonNull
    private final ItemsRepository itemsRepository;

    @NonNull
    private final FilesClient filesClient;

    @NonNull
    private final AppUtils appUtils;

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
        Map<String, String> headers = new HashMap();
        //headers.put("Cookie", JSESSIONID + "=" + appUtils.getCookie(JSESSIONID).get());
        filesClient.deleteByDomainId(JSESSIONID + "=" + appUtils.getCookie(JSESSIONID).get(), id);
    }
}
