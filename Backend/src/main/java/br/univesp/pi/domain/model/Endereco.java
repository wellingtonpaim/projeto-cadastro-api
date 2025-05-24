package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.UF;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private UF uf;

    public String getEnderecoFormatado() {
        StringBuilder sb = new StringBuilder();

        if (logradouro != null) sb.append(logradouro);
        if (numero != null) sb.append(", ").append(numero);
        if (complemento != null && !complemento.isBlank()) sb.append(" - ").append(complemento);
        if (bairro != null) sb.append(" ").append(bairro);
        if (cidade != null) sb.append(" - ").append(cidade);
        if (uf != null) sb.append(" - ").append(uf);

        return sb.toString();
    }

}
