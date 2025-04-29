package br.univesp.pi.repository;

import br.univesp.pi.domain.model.Fornecedor;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Registered
public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {

    List<Fornecedor> findByNomeOuRazaoSocialContainingIgnoreCase(String nomeOuRazaoSocial);
    List<Fornecedor> findByEmailContainingIgnoreCase(String email);
    Optional<Fornecedor> findByCpfOuCnpj(String fornecedor);
}
