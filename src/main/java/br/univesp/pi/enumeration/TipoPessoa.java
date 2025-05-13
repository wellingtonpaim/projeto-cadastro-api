package br.univesp.pi.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TipoPessoa {
    PJ, PF;

    @JsonCreator
    public static TipoPessoa fromString(String value) {
        return TipoPessoa.valueOf(value.toUpperCase());
    }
}
