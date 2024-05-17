package io.github.vinifillos.msavaliadorcredito.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoClienteDto {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}
