package itb.grupo5.skateflow.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import itb.grupo5.skateflow.model.entity.Categoria;
import itb.grupo5.skateflow.model.entity.Categoria;
import itb.grupo5.skateflow.rest.exception.ResourceNotFoundException;
import itb.grupo5.skateflow.service.CategoriaService;
 
@RestController
@RequestMapping("/categoria")
public class CategoriaController {
 
	private CategoriaService categoriaService;
	// Source -> Generate Constructor using Fields...
 
	public CategoriaController(CategoriaService categoriaService) {
		super();
		this.categoriaService = categoriaService;
	}
 
	@GetMapping("/test")
	public String getTest() {
		return "Olá, Categoria!";
	}
 
	@GetMapping("/findById/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable long id) {
 
		Categoria categoria = categoriaService.findById(id);
 
		if (categoria != null) {
			return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Categoria não encontrada!");
		}
 
	}
 
	@GetMapping("/findAll")
	public ResponseEntity<List<Categoria>> findAll() {
 
		List<Categoria> categorias = categoriaService.findAll();
 
		return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
 
	}
 
	@GetMapping("/listar")
	public ResponseEntity<List<Categoria>> listarCategorias() {
		List<Categoria> categorias = categoriaService.findAll(); // método do service
		if (categorias.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum categoria encontrado!");
		}
		return ResponseEntity.ok(categorias);
	}
 
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Categoria categoria) {
 
		Categoria _categoria = categoriaService.save(categoria);
 
		return ResponseEntity.ok().body("Categoria cadastrada com sucesso!");
	}
 
}