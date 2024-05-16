package io.github.vinifillos.msclientes.application.dto;

import io.github.vinifillos.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClieteSaveDto {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }
}
