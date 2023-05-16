package io.github.cursomicroservices.msavaliadorcredito.application.domain.model;

import lombok.Data;

@Data
public class CartaoCliente {
    private String nome;
    private String bandeira;
    private String limiteLiberado;
}
