package io.github.vinifillos.mscartoes.application.dto;

import io.github.vinifillos.mscartoes.domain.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoPorClienteDto {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartaoPorClienteDto fromModel(CartaoCliente model){
        return new CartaoPorClienteDto(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }
}
