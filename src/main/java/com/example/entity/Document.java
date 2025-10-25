package com.example.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Document(collection = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    private String id;
    private String name;
    private String type;
    private long size;
    private byte[] content;

    // Getters and Setters
}
