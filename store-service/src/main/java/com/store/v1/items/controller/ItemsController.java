package com.store.v1.items.controller;

import com.store.common.StoreConstants;
import com.store.v1.items.domain.Items;
import com.store.v1.items.domain.ItemsDto;
import com.store.v1.items.service.ItemsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(StoreConstants.STORE_BASE_URL + "/items")
public class ItemsController {

    @NonNull
    private final ItemsService itemsService;

    @PostMapping
    public Items save(@RequestBody Items items) {
        return itemsService.save(items);
    }

    @PutMapping(value = "{id}")
    public Items update(@PathVariable String id, @RequestBody Items items) {
        return itemsService.save(items);
    }

    @GetMapping
    public List<Items> get() {
        return itemsService.get().getItems();
    }

    @GetMapping(value = "{id}")
    public Items getById(@PathVariable String id) {
        return itemsService.findById(id).get();
    }

    @GetMapping(value = "/getByGroupId/{groupId}")
    public List<ItemsDto> getByGroupId(@PathVariable String groupId) {
        return itemsService.findByGroupId(groupId);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable String id) {
        itemsService.delete(id);
    }
}
