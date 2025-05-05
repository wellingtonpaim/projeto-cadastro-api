package br.univesp.pi.repository;

import br.univesp.pi.domain.model.ItemServico;
import br.univesp.pi.domain.model.ItemServicoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, ItemServicoId> {
    void deleteByServicoCodigo(Long servicoCodigo);
}
