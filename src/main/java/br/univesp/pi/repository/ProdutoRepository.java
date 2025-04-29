package br.univesp.pi.repository;

import br.univesp.pi.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(Long codigo);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
    List<Produto> findByFamiliaCodigo(Long codigoFamilia);

}
