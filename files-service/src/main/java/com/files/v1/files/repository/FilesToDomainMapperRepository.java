package com.files.v1.files.repository;

import com.files.v1.files.domain.FilesToDomainMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesToDomainMapperRepository extends MongoRepository<FilesToDomainMapper, String> {

    Optional<FilesToDomainMapper> findByDomainId(String domainId);

    void deleteByDomainId(String domainId);
}
