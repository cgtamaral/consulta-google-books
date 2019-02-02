package br.pucminas.consultagooglebooks.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.consultagooglebooks.api.Response;
import br.pucminas.consultagooglebooks.api.dtos.BookDTO;
import br.pucminas.consultagooglebooks.api.util.BooksSearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")
@Api(value = "books", description = "Recurso para consulta de livros via api do Google Books.", tags={ "books"})
public class BookController {
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@ApiOperation(value = "Consulta livros via api do Google Books. Deve ser informado pelo menos um dos parâmetros de pesquisa!", nickname = "findAllByFilter", notes = "", tags={ "books"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
		    @ApiResponse(code = 404, message = "Não foi encontrado nenhum livro para os parâmetros informados!"),
		    @ApiResponse(code = 400, message = "Ocorreu um erro ao pesquisar livros com os parâmetros informados!")})
	@GetMapping(value = "/books", produces = "application/json")
	public ResponseEntity<Object> findAllByFilter(@ApiParam(value = "Titulo de um livro a ser pesquisado. Quando o parâmetro isbn for informado o titulo será desconsiderado na pesquisa.", required = false)  @RequestParam(value = "title", required= false) String title,
			@ApiParam(value = "isbn de um livro a ser pesquisado.", required = false)  @RequestParam(value = "isbn", required= false) String isbn)
	{
		Response<List<BookDTO>> response = new Response<List<BookDTO>>();
		String query ="";
		if(isbn !=null && !isbn.isEmpty())
		{
			log.info("Consultando livro por isbn: {}" + isbn);
			query = "isbn:" + isbn;
		}
		else if(title != null && !title.isEmpty())
		{
			log.info("Consultando livros por title: {}" + title);
			query = "intitle:" + title;
		}
		else
		{
			log.info("Deve ser informado pelo menos um dos parâmetros de pesquisa!");
			response.getErrors().add("Deve ser informado pelo menos um dos parâmetros de pesquisa!");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		

		List<BookDTO> books = null;
		try
		{
			books = BooksSearchUtil.queryGoogleBooks(query);
		} 
		catch (Exception e)
		{
			log.info(e.getLocalizedMessage());
			response.getErrors().add(e.getLocalizedMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(books==null || books.size() == 0)
		{
			log.info("Nenhum livro foi encontrado para os parametros de pesquisa informados.");
			response.getErrors().add("Nenhum livro foi encontrado para os parametros de pesquisa informados.");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		response.setData(books);
		
		return ResponseEntity.ok().body(books);
	}
}
