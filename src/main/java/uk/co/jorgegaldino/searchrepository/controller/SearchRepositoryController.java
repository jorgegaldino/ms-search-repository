package uk.co.jorgegaldino.searchrepository.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import uk.co.jorgegaldino.searchrepository.dto.RepositoryDTO;
import uk.co.jorgegaldino.searchrepository.service.RepositoryService;


@Api(tags = {"Search Repository API"})
@SwaggerDefinition(tags = {
		@Tag(name = "Search Repository API", description = "API for Repositories")})	
@RestController
@Validated
@RequestMapping("/repository")
public class SearchRepositoryController {
	
	Logger logger = Logger.getLogger(SearchRepositoryController.class.getName());
	
	@Autowired
	private RepositoryService service;
	
	@Operation(summary = "Search Repositories", description = "Search Repositories by Language", tags = { "Search Repository API" })
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "success", 
	                content = @Content(array = @ArraySchema(schema = @Schema(implementation = RepositoryDTO.class)))), 
	        @ApiResponse(responseCode = "204", description = "Not Found")})			
	@GetMapping("{language}/page/{page}")
	public ResponseEntity<List<RepositoryDTO>> findByLanguageAndPage(
			@PathVariable(required = true, value ="language") String language, @Validated @PathVariable(required = false, value ="page") @Max(10) @Min(1) int page ) {
		List<RepositoryDTO> lista = service.findByLanguage(language, page); 
		
		if(lista.isEmpty()) {
			logger.info(String.format("%s Not found", language));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.ok(lista);
		

	}
	
	@Operation(summary = "Search Repositories", description = "Search All Repositories by Language", tags = { "Search Repository API" })
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "success", 
	                content = @Content(array = @ArraySchema(schema = @Schema(implementation = RepositoryDTO.class)))), 
	        @ApiResponse(responseCode = "204", description = "Not Found")})		
	@GetMapping("{language}/all")
	public ResponseEntity<List<RepositoryDTO>> findAllByLanguage(
			@PathVariable(required = true, value ="language") String language ) {
		
		List<RepositoryDTO> lista = service.findAllByLanguage(language); 
		
		if(lista.isEmpty()) {
			logger.info(String.format("%s Not found", language));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.ok(lista);

		

	}
	
	
	
	


}
