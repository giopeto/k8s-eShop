package com.store.v1.remote.call.files.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesToDomainMapper {

    @Id
    private String domainId;
    private List<String> fileIds;
}
