package com.store.v1.items.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {

    private String id;
    private String name;
    private String groupId;
    private String shortDescription;
    private String description;
    private Double price;
    private boolean archive;

    private List<String> fileIds;
}
