package itb.grupo5.skateflow.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import itb.grupo5.skateflow.model.entity.Lugar;
import itb.grupo5.skateflow.rest.exception.ResourceNotFoundException;
import itb.grupo5.skateflow.service.LugarService;

@RestController
@RequestMapping("/lugar")
public class LugarController {

    private final LugarService lugarService;

    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    @GetMapping("/test")
    public String test() {
        return "Olá, Lugar!";
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Lugar lugar) {
        Lugar novoLugar = lugarService.save(lugar);
        if (novoLugar != null) {
            return ResponseEntity.ok("Lugar cadastrado com sucesso");
        }
        throw new ResourceNotFoundException("Erro ao cadastrar lugar");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Lugar>> findAll() {
        List<Lugar> lugares = lugarService.findAll();
        if (lugares.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum lugar encontrado");
        }
        return ResponseEntity.ok(lugares);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Lugar> findById(@PathVariable Long id) {
        Lugar lugar = lugarService.findById(id);
        if (lugar != null) {
            return ResponseEntity.ok(lugar);
        }
        throw new ResourceNotFoundException("Lugar não encontrado");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Lugar> update(@PathVariable Long id, @RequestBody Lugar lugar) {
        Lugar atualizado = lugarService.update(id, lugar);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        throw new ResourceNotFoundException("Lugar não encontrado para atualização");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = lugarService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Lugar deletado com sucesso!");
        }
        throw new ResourceNotFoundException("Erro ao deletar lugar!");
    }
}
