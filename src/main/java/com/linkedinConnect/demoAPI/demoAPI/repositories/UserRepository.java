package com.linkedinConnect.demoAPI.demoAPI.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.linkedinConnect.demoAPI.demoAPI.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}