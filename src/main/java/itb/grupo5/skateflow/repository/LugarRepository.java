package itb.grupo5.skateflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import itb.grupo5.skateflow.model.entity.Lugar;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Long> {
    // Você pode adicionar métodos customizados aqui se quiser
}
