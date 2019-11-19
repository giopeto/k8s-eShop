package com.store.V1.items.repository;

import com.store.V1.items.domain.Items;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends MongoRepository<Items, String> {

    Optional<Items> findById(String id);

    void deleteById(String id);

    List<Items> findByGroupId(String groupId);
}
