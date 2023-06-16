package com.documentService.documentService.Service;

import com.documentService.documentService.Dao.DocumentDao;
import com.documentService.documentService.Entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    public ResponseEntity<Document> getDocument(int id){
        return documentDao.getDocument(id);
    }

    public List<Document> listDocument(){
        return documentDao.listDocument();
    }

    public ResponseEntity<Document> createDocument(Document document){
        return documentDao.createDocument(document);
    }

    public ResponseEntity<Document> updateDocument(int id,String content){
        return documentDao.updateDocument(id,content);
    }

    public ResponseEntity<Void> deleteDocument(int id){
        return documentDao.deleteDocument(id);
    }
}
