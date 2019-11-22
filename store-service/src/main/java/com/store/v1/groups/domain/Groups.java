package com.store.v1.groups.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Groups {

    @Id
    private String id;
    private String name;
}
