package com.store.V1.items.service;

import com.store.V1.items.domain.Items;
import com.store.V1.items.domain.ItemsList;
import com.store.V1.items.repository.ItemsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemsServiceImpl implements ItemsService {

	@NonNull private final ItemsRepository itemsRepository;


	@Autowired
	public ItemsServiceImpl(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

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
	}
}
