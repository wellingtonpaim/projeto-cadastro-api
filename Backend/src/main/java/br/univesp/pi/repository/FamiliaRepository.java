package br.univesp.pi.repository;

import br.univesp.pi.domain.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    List<Familia> findByNomeContainingIgnoreCase(String nome);
}
