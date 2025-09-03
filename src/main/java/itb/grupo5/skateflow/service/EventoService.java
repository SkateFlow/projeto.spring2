package itb.grupo5.skateflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import itb.grupo5.skateflow.model.entity.Evento;
import itb.grupo5.skateflow.repository.EventoRepository;
import itb.grupo5.skateflow.repository.UsuarioRepository;

@Service
public class EventoService {

	private EventoRepository eventoRepository;
	private UsuarioRepository usuarioRepository;

	public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
		super();
		this.eventoRepository = eventoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public Evento findById(long id) {
		Optional<Evento> evento = eventoRepository.findById(id);

		if (evento.isPresent()) {
			return evento.get();
		}
		return null;
	}

	public List<Evento> findAll() {
		List<Evento> eventos = eventoRepository.findAll();
		return eventos;
	}
}
