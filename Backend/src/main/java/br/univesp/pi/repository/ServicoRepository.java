package br.univesp.pi.repository;

import br.univesp.pi.domain.model.Pessoa;
import br.univesp.pi.domain.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByCliente(Pessoa cliente);

    List<Servico> findByCliente_CpfOuCnpj(String clienteCpfOuCnpj);

    Optional<Servico> findByCodigo(Long codigo);
}
