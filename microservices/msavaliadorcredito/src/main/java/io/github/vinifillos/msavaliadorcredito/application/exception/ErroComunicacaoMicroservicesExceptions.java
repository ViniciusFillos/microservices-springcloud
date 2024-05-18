package io.github.vinifillos.msavaliadorcredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservicesExceptions extends Exception{

    @Getter
    private Integer status;
    public ErroComunicacaoMicroservicesExceptions(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
