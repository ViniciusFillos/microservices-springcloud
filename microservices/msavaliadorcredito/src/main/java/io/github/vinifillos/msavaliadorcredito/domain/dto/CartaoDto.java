package io.github.vinifillos.msavaliadorcredito.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDto {

    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
