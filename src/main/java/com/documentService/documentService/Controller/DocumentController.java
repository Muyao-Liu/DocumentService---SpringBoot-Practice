package com.documentService.documentService.Controller;

import com.documentService.documentService.Entity.Document;
import com.documentService.documentService.Service.DocumentService;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("documents/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable int id){
        return documentService.getDocument(id);
    }

    @GetMapping("documents")
    public List<Document> listDocuments(){
        return documentService.listDocument();
    }

    @PostMapping("documents")
    public ResponseEntity<Document> createDocument(@RequestBody Document document){
        return documentService.createDocument(document);
    }

    @PutMapping("documents/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable int id,@RequestBody String content){
        return documentService.updateDocument(id,content);
    }

    @DeleteMapping("documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable int id){
        return documentService.deleteDocument(id);
    }
}
