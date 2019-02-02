package br.pucminas.consultagooglebooks.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTests 
{

	@Autowired
	private MockMvc mvc;
	
	private static final String BUSCAR_BOOK_URL = "/v1/public/books";
	
	@Test
	public void testFindAllByFilter() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_BOOK_URL + "?isbn=0553897845")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
