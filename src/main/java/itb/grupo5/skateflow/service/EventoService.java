package itb.grupo5.skateflow.service;

import java.time.LocalDateTime;
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
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // CREATE
    public Evento save(Evento evento) {
        // Define a data de cadastro como agora, se não estiver setada
        if (evento.getDataCadastro() == null) {
            evento.setDataCadastro(LocalDateTime.now());
        }

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

            // Só atualiza o usuário se vier um válido
            if (eventoAtualizado.getUsuario_id() != null) {
                eventoExistente.setUsuario_id(eventoAtualizado.getUsuario_id());
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
