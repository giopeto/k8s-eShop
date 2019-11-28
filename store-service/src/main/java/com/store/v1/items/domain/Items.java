package com.store.v1.items.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Items {

    @Id
    private String id;
    @NonNull
    private String name;
    private String groupId;
    private String shortDescription;
    private String description;
    private Double price;
    private boolean archive;
    private Date date;
}
