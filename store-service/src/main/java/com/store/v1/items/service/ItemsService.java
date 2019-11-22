package com.store.v1.items.service;

import com.store.v1.items.domain.Items;
import com.store.v1.items.domain.ItemsList;

import java.util.Optional;

public interface ItemsService {

    public Items save(Items item);

    public ItemsList get();

    public Optional<Items> findById(String id);

    public ItemsList findByGroupId(String groupId);

    public void delete(String id);
}
