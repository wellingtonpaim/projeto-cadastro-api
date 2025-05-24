package br.univesp.pi.domain.dto.response;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import br.univesp.pi.enumeration.TipoPessoa;
import lombok.Data;

import java.util.List;

@Data
public class FornecedorResponseDTO {

    private String cpfOuCnpj;
    private TipoPessoa tipoPessoa;
    private String nomeOuRazaoSocial;
    private String email;
    private List<Telefone> telefones;
    private Endereco endereco;
}
