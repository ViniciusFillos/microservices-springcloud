package io.github.vinifillos.mscartoes.application.dto;

import io.github.vinifillos.mscartoes.domain.CartaoBandeira;
import io.github.vinifillos.mscartoes.domain.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteDto {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteDto fromModel(CartaoCliente model){
        return new CartoesPorClienteDto(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }
}
