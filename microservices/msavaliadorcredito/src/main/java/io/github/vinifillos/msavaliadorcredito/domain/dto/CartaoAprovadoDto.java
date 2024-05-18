package io.github.vinifillos.msavaliadorcredito.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovadoDto {

    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
