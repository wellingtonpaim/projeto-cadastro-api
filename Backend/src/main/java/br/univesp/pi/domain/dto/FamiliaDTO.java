package br.univesp.pi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FamiliaDTO {

    @NotBlank
    private String nome;
}
