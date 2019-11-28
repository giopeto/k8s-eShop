package com.files.v1.files.service;

import com.files.v1.files.domain.FilesToDomainMapper;
import com.files.v1.files.domain.FilesUpload;
import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FilesServiceImpl implements FilesService {

    @NonNull
    private final GridFsOperations gridFsOperations;
    @NonNull
    private final FilesToDomainMapperService filesToDomainMapperService;
    @NonNull
    private final GridFSBucket gridFSBucket;

    @Override
    public FilesToDomainMapper store(FilesUpload filesUpload) {
        filesUpload.getFiles().forEach(file -> {
            try (InputStream inputStream = file.getInputStream()) {
                filesToDomainMapperService.addFileId(filesUpload.getDomainId(),
                        gridFsOperations.store(inputStream, file.getOriginalFilename(), file.getContentType(), new BasicDBObject()).toString());
            } catch (IOException e) {
                log.error("Error while storing file with name {}: {}", file.getOriginalFilename(), e.getMessage());
            }
        });

        return filesToDomainMapperService.getByDomainId(filesUpload.getDomainId());
    }

    public GridFsResource getById(String id) {
        GridFSFile gridFile = gridFsOperations.findOne(new Query(GridFsCriteria.where("_id").is(id)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFile.getFilename());

        return new GridFsResource(gridFile, gridFSDownloadStream);
    }

    @Override
    public void delete(String id, String domainId) {
        gridFsOperations.delete(new Query(GridFsCriteria.where("_id").is(id)));
        filesToDomainMapperService.removeFileByFileId(domainId, id);
    }

    @Override
    public void delete(List<String> ids, String domainId) {
        gridFsOperations.delete(new Query(GridFsCriteria.where("_id").is(ids)));
    }

    @Override
    public void deleteByDomainId(String domainId) {
        filesToDomainMapperService.getByDomainId(domainId).getFileIds().forEach(fileId ->
                gridFsOperations.delete(new Query(GridFsCriteria.where("_id").is(fileId))));
        filesToDomainMapperService.deleteByDomainId(domainId);
    }
}
