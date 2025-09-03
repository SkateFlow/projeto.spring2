package itb.grupo5.skateflow.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itb.grupo5.skateflow.model.entity.Evento;
import itb.grupo5.skateflow.rest.exception.ResourceNotFoundException;
import itb.grupo5.skateflow.service.EventoService;

@RestController
@RequestMapping("/evento")
public class EventoController {
	
		
		private EventoService eventoService;
		
		public EventoController(EventoService eventoService) {
			super();
			this.eventoService = eventoService;
	}
		
		@GetMapping("/test")
		public String getTest() {
			return "Olá, Evento!";
		}
		
		@GetMapping("/findById/{id}")
		public ResponseEntity<Evento> findById(@PathVariable long id) {
			
			Evento evento = eventoService.findById(id);
			
			if (evento != null) {
				return new ResponseEntity<Evento>(evento, HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("Evento não encontrado!");
			}
		}
		
		@GetMapping("/findAll")
		public ResponseEntity<List<Evento>> findAll(){
			
			List<Evento> eventos = eventoService.findAll();
			
			return new ResponseEntity<List<Evento>>(eventos, HttpStatus.OK);
		}
		
		@GetMapping("/listar")
		public ResponseEntity<List<Evento>> listarEventos() {
		    List<Evento> eventos = eventoService.findAll(); // método do service
		    if (eventos.isEmpty()) {
		        throw new ResourceNotFoundException("Nenhum evento encontrado!");
		    }
		    return ResponseEntity.ok(eventos);
		}		

	

}
