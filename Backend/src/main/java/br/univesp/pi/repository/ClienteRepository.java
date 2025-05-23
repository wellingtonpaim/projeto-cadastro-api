package br.univesp.pi.repository;

import br.univesp.pi.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findByNomeOuRazaoSocialContainingIgnoreCase(String nomeOuRazaoSocial);
    List<Cliente> findByEmailContainingIgnoreCase(String email);
    Optional<Cliente> findByCpfOuCnpj(String cliente);
}

