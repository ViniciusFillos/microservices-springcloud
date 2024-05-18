package io.github.vinifillos.msavaliadorcredito.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoClienteDto {
    private List<CartaoAprovadoDto> cartoes;
}
