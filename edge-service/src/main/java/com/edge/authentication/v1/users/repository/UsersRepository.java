package com.edge.authentication.v1.users.repository;

import com.edge.authentication.v1.users.domain.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

    Users findOneByEmail(String email);
}
