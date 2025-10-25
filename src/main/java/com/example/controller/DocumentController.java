//package com.example.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.entity.Document;
//import com.example.service.DocumentService;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:5173") // React dev server
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//    private final DocumentService service;
//
//    public DocumentController(DocumentService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<Document> getAllDocuments() {
//        return service.getAll();
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
//        try {
//            String result = service.upload(file);
//            if ("EXISTS".equals(result)) {
//                return ResponseEntity.status(409).body("File already exists");
//            }
//            return ResponseEntity.ok("Uploaded successfully");
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Upload failed");
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable String id) {
//        service.delete(id);
//        return ResponseEntity.ok("Deleted successfully");
//    }
//}


package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Document;
import com.example.service.DocumentService;

@RestController
//@CrossOrigin(origins = "*") // Allow all origins or change to your frontend URL
@RequestMapping("/api/documents")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://webrepo1vercel.vercel.app")

public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    // Get all documents
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = service.getAll();
        return ResponseEntity.ok(documents);
    }

    // Upload a file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            String result = service.upload(file);

            if ("EXISTS".equals(result)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("File with the same name already exists");
            }

            return ResponseEntity.ok("File uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable String id) {
        try {
            // Call service.delete(id) which returns true if file was deleted
            boolean deleted = service.delete(id);

            if (deleted) {
                return ResponseEntity.ok("File deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found or already deleted");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File deletion failed: " + e.getMessage());
        }
    }

    
}
