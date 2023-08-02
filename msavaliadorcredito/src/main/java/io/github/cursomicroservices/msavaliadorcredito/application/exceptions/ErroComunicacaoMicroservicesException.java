package io.github.cursomicroservices.msavaliadorcredito.application.exceptions;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception {

    @Getter
    private Integer status;

    public ErroComunicacaoMicroservicesException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
