package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entity.Document;

public interface DocumentRepository extends MongoRepository<Document, String> {
    boolean existsByName(String name);
}
