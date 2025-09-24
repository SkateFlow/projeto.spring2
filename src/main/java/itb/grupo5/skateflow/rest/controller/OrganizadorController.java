package itb.grupo5.skateflow.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import itb.grupo5.skateflow.model.entity.Organizador;
import itb.grupo5.skateflow.rest.exception.ResourceNotFoundException;
import itb.grupo5.skateflow.service.OrganizadorService;

@RestController
@RequestMapping("/organizador")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    public OrganizadorController(OrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Olá, Organizador!";
    }

    // GET por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<Organizador> findById(@PathVariable Long id) {
        Organizador organizador = organizadorService.findById(id);
        if (organizador != null) {
            return ResponseEntity.ok(organizador);
        } else {
            throw new ResourceNotFoundException("Organizador não encontrado!");
        }
    }

    // GET todos
    @GetMapping("/findAll")
    public ResponseEntity<List<Organizador>> findAll() {
        List<Organizador> organizadores = organizadorService.findAll();
        return ResponseEntity.ok(organizadores);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Organizador>> listarOrganizadores() {
        List<Organizador> organizadores = organizadorService.findAll();
        if (organizadores.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum organizador encontrado!");
        }
        return ResponseEntity.ok(organizadores);
    }

    // POST (create)
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Organizador organizador) {
        Organizador salvo = organizadorService.save(organizador);
        if (salvo != null) {
            return ResponseEntity.ok("Organizador cadastrado com sucesso!");
        }
        throw new ResourceNotFoundException("Erro ao cadastrar organizador.");
    }

    // PUT (update)
    @PutMapping("/update/{id}")
    public ResponseEntity<Organizador> update(@PathVariable Long id, @RequestBody Organizador organizador) {
        Organizador atualizado = organizadorService.update(id, organizador);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        throw new ResourceNotFoundException("Organizador não encontrado para atualização.");
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean deletado = organizadorService.delete(id);
        if (deletado) {
            return ResponseEntity.ok("Organizador deletado com sucesso!");
        }
        throw new ResourceNotFoundException("Erro ao deletar organizador!");
    }
}
