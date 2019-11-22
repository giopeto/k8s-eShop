package com.files.v1.files.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "filesToDomainMapper")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesToDomainMapper {

    @Id
    private String domainId;
    private List<String> fileIds;
}
