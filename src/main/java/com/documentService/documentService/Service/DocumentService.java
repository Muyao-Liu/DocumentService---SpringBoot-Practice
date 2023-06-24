package com.documentService.documentService.Service;

import com.documentService.documentService.Dao.DocumentRepository;
import com.documentService.documentService.Entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository=documentRepository;
    }

    public ResponseEntity<Document> getDocument(int id){
        try {
            Optional<Document> documentOptional = documentRepository.findById(id);
            return ResponseEntity.ok(documentOptional.get());
        }catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Document>> listDocument(){
        List<Document> documentOptional = documentRepository.findAll();
        return ResponseEntity.ok(documentOptional);
    }

    public ResponseEntity<Document> createDocument(Document document){
        try {
            if (documentRepository.existsById(document.getId())) {
                throw new IllegalArgumentException();
            }
            Document documentCreated=documentRepository.save(document);
            return ResponseEntity.ok(documentCreated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    public ResponseEntity<Document> updateDocument(int id,String content){
        try {
            Optional<Document> documentOptional = documentRepository.findById(id);
            Document document=documentOptional.get();
            document.setContent(content);
            Document documentUpdated=documentRepository.save(document);
            return ResponseEntity.ok(documentUpdated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteDocument(int id){
        try {
            Optional<Document> documentOptional = documentRepository.findById(id);
            documentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
