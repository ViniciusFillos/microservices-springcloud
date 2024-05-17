package io.github.vinifillos.mscartoes.application.dto;

import io.github.vinifillos.mscartoes.domain.CartaoBandeira;
import io.github.vinifillos.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveDto {

    private String nome;
    private CartaoBandeira bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
