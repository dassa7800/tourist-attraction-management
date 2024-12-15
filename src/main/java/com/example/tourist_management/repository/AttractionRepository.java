package com.example.tourist_management.repository;

import com.example.tourist_management.model.Attraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends MongoRepository<Attraction, String> {
    // Custom query methods (optional) can be defined here
}
