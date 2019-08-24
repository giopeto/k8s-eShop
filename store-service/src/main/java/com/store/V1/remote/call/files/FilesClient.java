package com.store.V1.remote.call.files;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "files-service-svc")
public interface FilesClient {

    @DeleteMapping("/files/V1/files/domainId/{id}")
    void deleteByDomainId(@RequestHeader("Cookie") String sessionId, @PathVariable("id") String id);

}
