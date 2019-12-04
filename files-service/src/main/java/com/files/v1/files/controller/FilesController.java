package com.files.v1.files.controller;

import com.files.v1.files.domain.FilesToDomainMapper;
import com.files.v1.files.domain.FilesUpload;
import com.files.v1.files.service.FilesService;
import com.files.v1.files.service.FilesToDomainMapperService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.files.common.FilesConstants.FILES_BASE_URL;

@RestController
@AllArgsConstructor
@RequestMapping(FILES_BASE_URL + "/files")
public class FilesController {

    @NonNull
    private final FilesService filesService;
    @NonNull
    private final FilesToDomainMapperService filesToDomainMapperService;

    @PostMapping
    public FilesToDomainMapper save(@ModelAttribute FilesUpload filesUpload) {
        return filesService.store(filesUpload);
    }

    @GetMapping(value = "{domainId}")
    public FilesToDomainMapper getByDomainId(@PathVariable String domainId) {
        return filesToDomainMapperService.getByDomainId(domainId);
    }

    @PostMapping("domain-ids")
    public List<FilesToDomainMapper> getByDomainIds(@RequestBody List<String> domainIds) {
        return filesToDomainMapperService.getByDomainIds(domainIds);
    }

    @GetMapping(value = "file/{fileId}")
    public ResponseEntity<InputStreamResource> getById(@PathVariable String fileId) throws IOException {
        GridFsResource gridFsFile = filesService.getById(fileId);

        return ResponseEntity.ok()
                .contentLength(gridFsFile.contentLength())
                .contentType(MediaType.parseMediaType(gridFsFile.getContentType()))
                .body(new InputStreamResource(gridFsFile.getInputStream()));
    }

    @DeleteMapping(value = "{id}/domainId/{domainId}")
    public void delete(@PathVariable String id, @PathVariable String domainId) {
        filesService.delete(id, domainId);
    }

    @DeleteMapping(value = "domainId/{domainId}")
    public void deleteByDomainId(@PathVariable String domainId) {
        filesService.deleteByDomainId(domainId);
    }

}
