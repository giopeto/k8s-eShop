package com.files.v1.files.service;

import com.files.v1.files.domain.FilesToDomainMapper;
import com.files.v1.files.repository.FilesToDomainMapperRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Service
public class FilesToDomainMapperServiceImpl implements FilesToDomainMapperService {

    @NonNull
    private final FilesToDomainMapperRepository filesToDomainMapperRepository;

    @Autowired
    public FilesToDomainMapperServiceImpl(FilesToDomainMapperRepository filesToDomainMapperRepository) {
        this.filesToDomainMapperRepository = filesToDomainMapperRepository;
    }

    @Override
    public FilesToDomainMapper save(FilesToDomainMapper filesToDomainMapper) {
        return filesToDomainMapperRepository.save(filesToDomainMapper);
    }

    @Override
    public FilesToDomainMapper getByDomainId(String domainId) {
        Optional<FilesToDomainMapper> filesToDomainMapper = filesToDomainMapperRepository.findByDomainId(domainId);
        if (filesToDomainMapper.isPresent()) {
            return filesToDomainMapper.get();
        } else {
            return new FilesToDomainMapper(domainId, new ArrayList<>());
        }
    }

    @Override
    public List<FilesToDomainMapper> getByDomainIds(List<String> domainIds) {
        return filesToDomainMapperRepository.findByDomainIdIn(domainIds);
    }

    @Override
    public FilesToDomainMapper addFileId(String domainId, String fileId) {
        FilesToDomainMapper filesToDomainMapper = getByDomainId(domainId);
        if (filesToDomainMapper == null) {
            filesToDomainMapper = new FilesToDomainMapper(domainId, singletonList(fileId));
        } else {
            filesToDomainMapper.getFileIds().add(fileId);
        }

        return save(filesToDomainMapper);
    }

    @Override
    public void deleteByDomainId(String domainId) {
        filesToDomainMapperRepository.deleteByDomainId(domainId);
    }

    @Override
    public void removeFileByFileId(String domainId, String fileId) {
        FilesToDomainMapper filesToDomainMapper = getByDomainId(domainId);

        filesToDomainMapper.setFileIds(filesToDomainMapper.getFileIds().stream()
                .filter(thisFileId -> !thisFileId.equals(fileId))
                .collect(Collectors.toList()));
        save(filesToDomainMapper);
    }
}
