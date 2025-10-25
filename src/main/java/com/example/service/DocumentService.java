package com.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Document;
import com.example.repository.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public List<Document> getAll() {
        return repository.findAll();
    }

    public String upload(MultipartFile file) throws IOException {
        if (repository.existsByName(file.getOriginalFilename())) {
            return "EXISTS";
        }
        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setType(file.getContentType());
        doc.setSize(file.getSize());
        doc.setContent(file.getBytes());
        repository.save(doc);
        return "UPLOADED";
    }

//    public void delete(String id) {
//        repository.deleteById(id);
//    }
    public boolean delete(String id) {
        Optional<Document> doc = repository.findById(id);
        if (doc.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
