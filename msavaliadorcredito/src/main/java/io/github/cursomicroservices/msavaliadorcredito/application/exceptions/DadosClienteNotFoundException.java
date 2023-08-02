package io.github.cursomicroservices.msavaliadorcredito.application.exceptions;

public class DadosClienteNotFoundException extends Exception {

    public DadosClienteNotFoundException() {
        super("Dados do cliente não encontrados para o cpf informado.");
    }
}
