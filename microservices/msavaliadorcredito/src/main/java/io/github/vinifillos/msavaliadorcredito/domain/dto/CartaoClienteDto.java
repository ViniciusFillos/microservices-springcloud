package io.github.vinifillos.msavaliadorcredito.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class CartaoClienteDto {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}
