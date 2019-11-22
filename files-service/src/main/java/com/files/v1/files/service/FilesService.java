package com.files.v1.files.service;

import com.files.v1.files.domain.FilesToDomainMapper;
import com.files.v1.files.domain.FilesUpload;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.util.List;

public interface FilesService {

    public FilesToDomainMapper store(FilesUpload filesUpload);

    public GridFsResource getById(String id);

    public void delete(String id, String domainId);

    public void delete(List<String> ids, String domainId);

    public void deleteByDomainId(String domainId);
}
