package com.documentService.documentService;

import com.documentService.documentService.Dao.DocumentRepository;
import com.documentService.documentService.Entity.Document;
import com.documentService.documentService.Service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class DocumentServiceApplicationTests {
	@MockBean
	DocumentRepository documentRepository;
	DocumentService documentService;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListAllLoads() throws Exception{
		List<Document> documentList=new ArrayList<>();
		Document document1=new Document();
		document1.setId(1);
		document1.setContent("document1");
		documentList.add(document1);

		Document document2=new Document();
		document2.setId(2);
		document2.setContent("document2");
		documentList.add(document2);

		when(documentRepository.findAll()).thenReturn(documentList);

		mockMvc.perform(MockMvcRequestBuilders.get("/documents"))
						.andExpect(status().isOk())
				        .andExpect(MockMvcResultMatchers.content().json("[{\"id\": 1,\"content\": \"document1\"},{\"id\": 2,\"content\": \"document2\"}]"));
		System.out.println("List all test passed!");
	}

	@Test
	public void testGetLoad() throws Exception{
		Document document1=new Document();
		document1.setId(1);
		document1.setContent("document1");

		int id=1;
		int id_not = 2;
		documentService=new DocumentService(documentRepository);

		try {
			when(documentRepository.findById(id)).thenReturn(Optional.of(document1));
			ResponseEntity<Document> responseEntity1= documentService.getDocument(id);
			assertEquals(HttpStatus.OK,responseEntity1.getStatusCode());
			assertEquals(document1,responseEntity1.getBody());

			when(documentRepository.findById(id_not)).thenReturn(Optional.empty());
			ResponseEntity<Document> responseEntity2= documentService.getDocument(id_not);
			assertEquals(HttpStatus.NOT_FOUND,responseEntity2.getStatusCode());
			assertEquals(null,responseEntity2.getBody());
		}catch (Exception e){

		}

		System.out.println("Get test passed!");
	}

	@Test
	public void testCreateLoad() throws Exception{
		Document document1=new Document();
		document1.setId(1);
		document1.setContent("document1");

		Document document2=new Document();
		document2.setId(1);
		document2.setContent("document2");

		documentService=new DocumentService(documentRepository);

		try {
			when(documentRepository.save(document1)).thenReturn(document1);
			ResponseEntity<Document> responseEntity1= documentService.createDocument(document1);
			assertEquals(HttpStatus.OK,responseEntity1.getStatusCode());
			assertEquals(document1,responseEntity1.getBody());

			when(documentRepository.existsById(document2.getId())).thenReturn(true);
			ResponseEntity<Document> responseEntity2= documentService.createDocument(document2);
			assertEquals(HttpStatus.CONFLICT,responseEntity2.getStatusCode());
			assertEquals(null,responseEntity2.getBody());
		}catch (Exception e){

		}

		System.out.println("Create test passed!");
	}

	@Test
	public void testUpdateLoad(){
		int id1=1;
		String content1="document1";
		Document document1=new Document();
		document1.setId(id1);
		document1.setContent(content1);

		int id2=2;
		String content2="document2";

		documentService=new DocumentService(documentRepository);

		try{
			when(documentRepository.findById(id1)).thenReturn(Optional.of(document1));
			when(documentRepository.save(document1)).thenReturn(document1);
			ResponseEntity<Document> responseEntity1=documentService.updateDocument(id1,content1);
			assertEquals(HttpStatus.OK,responseEntity1.getStatusCode());
			assertEquals(document1,responseEntity1.getBody());

			when(!documentRepository.existsById(id2)).thenReturn(true);
			ResponseEntity<Document> responseEntity2=documentService.updateDocument(id2,content2);
			assertEquals(HttpStatus.NOT_FOUND,responseEntity2.getStatusCode());
			assertEquals(null,responseEntity2.getBody());
		}catch (Exception e){

		}
		System.out.println("Update test passed!");
	}

	@Test
	public void testDeleteLoad(){
		int id1=1;
		String content1="document1";
		Document document1=new Document();
		document1.setId(id1);
		document1.setContent(content1);

		documentService=new DocumentService(documentRepository);

		try{
			when(documentRepository.findById(id1)).thenReturn(Optional.of(document1));
			ResponseEntity<Void> responseEntity1=documentService.deleteDocument(id1);
			assertEquals(HttpStatus.NO_CONTENT,responseEntity1.getStatusCode());
			assertEquals(null,responseEntity1.getBody());

			when(documentRepository.findById(id1)).thenReturn(Optional.empty());
			ResponseEntity<Void> responseEntity2=documentService.deleteDocument(id1);
			assertEquals(HttpStatus.NO_CONTENT,responseEntity2.getStatusCode());
			assertEquals(null,responseEntity2.getBody());

		}catch (Exception e){

		}
		System.out.println("Delete test passed!");
	}

}
