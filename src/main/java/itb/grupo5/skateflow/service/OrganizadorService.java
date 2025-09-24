package itb.grupo5.skateflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itb.grupo5.skateflow.model.entity.Organizador;
import itb.grupo5.skateflow.model.entity.Usuario;
import itb.grupo5.skateflow.repository.OrganizadorRepository;
import itb.grupo5.skateflow.repository.UsuarioRepository;

@Service
public class OrganizadorService {

	private final OrganizadorRepository organizadorRepository;
	private final UsuarioRepository usuarioRepository;

	public OrganizadorService(OrganizadorRepository organizadorRepository, UsuarioRepository usuarioRepository) {
		this.organizadorRepository = organizadorRepository;
		this.usuarioRepository = usuarioRepository;
	}

	// CREATE
	@Transactional
	public Organizador save(Organizador organizador) {
		// Verifica se o usuário está presente
		if (organizador.getUsuario_id() == null || organizador.getUsuario_id().getId() == 0) {
			throw new IllegalArgumentException("Usuário inválido ou não informado.");
		}

		// Verifica se o usuário realmente existe no banco
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(organizador.getUsuario_id().getId());
		if (!usuarioOptional.isPresent()) {
			throw new IllegalArgumentException("Usuário não encontrado.");
		}

		return organizadorRepository.save(organizador);
	}

	// READ - Buscar por ID
	public Organizador findById(Long id) {
		Optional<Organizador> organizador = organizadorRepository.findById(id);
		return organizador.orElse(null);
	}

	// READ - Listar todos
	public List<Organizador> findAll() {
		return organizadorRepository.findAll();
	}

	// UPDATE
	@Transactional
	public Organizador update(Long id, Organizador organizadorAtualizado) {
		Optional<Organizador> optionalOrganizador = organizadorRepository.findById(id);

		if (optionalOrganizador.isPresent()) {
			Organizador organizadorExistente = optionalOrganizador.get();

			// Atualiza os campos
			organizadorExistente.setNome(organizadorAtualizado.getNome());
			organizadorExistente.setCpf_cnpj(null);
			organizadorExistente.setDataNascimento(organizadorAtualizado.getDataNascimento());
			organizadorExistente.setTelefone(organizadorAtualizado.getTelefone());
			organizadorExistente.setEmail(organizadorAtualizado.getEmail());
			organizadorExistente.setWebsite(organizadorAtualizado.getWebsite());
			organizadorExistente.setLogradouro(organizadorAtualizado.getLogradouro());
			organizadorExistente.setNumResidencia(organizadorAtualizado.getNumResidencia());
			organizadorExistente.setCep(organizadorAtualizado.getCep());
			organizadorExistente.setBairro(organizadorAtualizado.getBairro());
			organizadorExistente.setCidade(organizadorAtualizado.getCidade());
			organizadorExistente.setUf(organizadorAtualizado.getUf());
			organizadorExistente.setComplemento(organizadorAtualizado.getComplemento());
			organizadorExistente.setStatusOrganizador(null);

			// Verifica e atualiza o usuário
			if (organizadorAtualizado.getUsuario_id() != null) {
				Optional<Usuario> usuarioOptional = usuarioRepository
						.findById(organizadorAtualizado.getUsuario_id().getId());

				if (usuarioOptional.isPresent()) {
					organizadorExistente.setUsuario_id(organizadorAtualizado.getUsuario_id());
				} else {
					throw new IllegalArgumentException("Usuário não encontrado.");
				}
			}

			return organizadorRepository.save(organizadorExistente);
		}

		return null;
	}

	// DELETE
	public boolean delete(Long id) {
		Optional<Organizador> optionalOrganizador = organizadorRepository.findById(id);
		if (optionalOrganizador.isPresent()) {
			organizadorRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
