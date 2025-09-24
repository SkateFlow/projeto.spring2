package itb.grupo5.skateflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itb.grupo5.skateflow.model.entity.Organizador;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long>{

}
