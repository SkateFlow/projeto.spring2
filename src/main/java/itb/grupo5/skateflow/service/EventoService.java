package itb.grupo5.skateflow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itb.grupo5.skateflow.model.entity.Evento;
import itb.grupo5.skateflow.model.entity.Lugar;
import itb.grupo5.skateflow.model.entity.Usuario;
import itb.grupo5.skateflow.repository.EventoRepository;
import itb.grupo5.skateflow.repository.LugarRepository;
import itb.grupo5.skateflow.repository.UsuarioRepository;

@Service
public class EventoService {

	private EventoRepository eventoRepository;
	private UsuarioRepository usuarioRepository;
	private LugarRepository lugarRepository;

	public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository,
			LugarRepository lugarRepository) {
		this.eventoRepository = eventoRepository;
		this.usuarioRepository = usuarioRepository;
		this.lugarRepository = lugarRepository;
	}

	// CREATE
	@Transactional
	public Evento save(Evento evento) {
		// Define a data de cadastro como agora, se não estiver setada
		if (evento.getDataCadastro() == null) {
			evento.setDataCadastro(LocalDateTime.now());
		}

		// Valida se o usuário existe
		if (evento.getUsuario_id() == null || evento.getUsuario_id().getId() == 0) {
			throw new IllegalArgumentException("Usuário inválido ou não informado.");
		}

		// Valida se o lugar existe
		if (evento.getLugar_id() == null || evento.getLugar_id().getId() == 0) {
			throw new IllegalArgumentException("Lugar inválido ou não informado.");
		}

		// Verificar se o lugar existe no banco
		Optional<Lugar> lugarOptional = lugarRepository.findById(evento.getLugar_id().getId());
		if (!lugarOptional.isPresent()) {
			throw new IllegalArgumentException("Lugar não encontrado.");
		}

		// Verificar se o usuário existe no banco
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(evento.getUsuario_id().getId());
		if (!usuarioOptional.isPresent()) {
			throw new IllegalArgumentException("Usuário não encontrado.");
		}

		// Salvando o evento
		return eventoRepository.save(evento);
	}

	// READ - Buscar por ID
	public Evento findById(long id) {
		Optional<Evento> evento = eventoRepository.findById(id);
		return evento.orElse(null);
	}

	// READ - Listar todos
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	// UPDATE
	@Transactional
	public Evento update(Long id, Evento eventoAtualizado) {
		Optional<Evento> optionalEvento = eventoRepository.findById(id);

		if (optionalEvento.isPresent()) {
			Evento eventoExistente = optionalEvento.get();

			// Atualizando os campos da entidade Evento
			eventoExistente.setNome(eventoAtualizado.getNome());
			eventoExistente.setInfo(eventoAtualizado.getInfo());
			eventoExistente.setFoto(eventoAtualizado.getFoto());
			eventoExistente.setDataInicio(eventoAtualizado.getDataInicio());
			eventoExistente.setDataFim(eventoAtualizado.getDataFim());
			eventoExistente.setStatusEvento(eventoAtualizado.getStatusEvento());

			// Atualiza o usuário se for válido
			if (eventoAtualizado.getUsuario_id() != null) {
				Optional<Usuario> usuarioOptional = usuarioRepository
						.findById(eventoAtualizado.getUsuario_id().getId());
				if (usuarioOptional.isPresent()) {
					eventoExistente.setUsuario_id(eventoAtualizado.getUsuario_id());
				} else {
					throw new IllegalArgumentException("Usuário não encontrado.");
				}
			}

			// Atualiza o lugar se for válido
			if (eventoAtualizado.getLugar_id() != null) {
				Optional<Lugar> lugarOptional = lugarRepository.findById(eventoAtualizado.getLugar_id().getId());
				if (lugarOptional.isPresent()) {
					eventoExistente.setLugar_id(eventoAtualizado.getLugar_id());
				} else {
					throw new IllegalArgumentException("Lugar não encontrado.");
				}
			}

			return eventoRepository.save(eventoExistente);
		}

		return null;
	}

	// DELETE
	public boolean delete(Long id) {
		Optional<Evento> optionalEvento = eventoRepository.findById(id);
		if (optionalEvento.isPresent()) {
			eventoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
