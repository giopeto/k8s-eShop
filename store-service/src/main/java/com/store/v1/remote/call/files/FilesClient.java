package com.store.v1.remote.call.files;

import com.store.v1.remote.call.files.domain.FilesToDomainMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "files-service-svc")
public interface FilesClient {

    @PostMapping("/files/v1/files/domain-ids")
    List<FilesToDomainMapper> getByDomainIds(@RequestHeader("Cookie") String sessionId, @RequestBody List<String> domainIds);

    @DeleteMapping("/files/v1/files/domainId/{id}")
    void deleteByDomainId(@RequestHeader("Cookie") String sessionId, @PathVariable("id") String id);
}
