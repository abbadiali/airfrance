package com.airfrance.test.repository;

import com.airfrance.test.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
/**
 * @param username
 * @return boolean, checks if user username already exists
 */
    boolean existsByUsername(String username);
}
