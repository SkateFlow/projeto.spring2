package itb.grupo5.skateflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import itb.grupo5.skateflow.model.entity.Categoria;
import itb.grupo5.skateflow.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

	private CategoriaRepository categoriaRepository;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public Categoria findById(long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElse(null);
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Transactional
	public Categoria update(Long id, Categoria categoriaAtualizada) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

		if (optionalCategoria.isPresent()) {
			Categoria categoriaExistente = optionalCategoria.get();
			categoriaExistente.setNome(categoriaAtualizada.getNome());
			return categoriaRepository.save(categoriaExistente);
		}

		return null;
	}

	@Transactional
	public boolean delete(Long id) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
		if (optionalCategoria.isPresent()) {
			categoriaRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
