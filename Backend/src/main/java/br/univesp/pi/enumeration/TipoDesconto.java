package br.univesp.pi.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TipoDesconto {
    PORCENTAGEM, VALOR;

    @JsonCreator
    public static TipoDesconto fromString(String value) {
        return TipoDesconto.valueOf(value.toUpperCase());
    }
}
