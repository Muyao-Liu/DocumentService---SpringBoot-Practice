package com.documentService.documentService.Dao;

import com.documentService.documentService.Entity.Document;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentDao {
    Map<Integer,Document> map=new HashMap<>();

    public ResponseEntity<Document> getDocument(int id){
        if(!map.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map.get(id));
    }

    public List<Document> listDocument(){
        return map.values().stream().toList();
    }

    public ResponseEntity<Document> createDocument(Document document){
        //already exists document id
        if(map.containsKey(document.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        map.put(document.getId(),document);
        return ResponseEntity.ok(map.get(document.getId()));
    }

    public ResponseEntity<Document> updateDocument(int id,String content){
        if(!map.containsKey(id)){
            return ResponseEntity.notFound().build();
        }

        Document document=map.get(id);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            String parsed = jsonNode.get("content").asText();
            document.setContent(parsed);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        map.put(id,document);
        return ResponseEntity.ok(map.get(id));
    }

    public ResponseEntity<Void> deleteDocument(int id){
        if(!map.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        map.remove(id);
        return ResponseEntity.noContent().build();
    }
}
