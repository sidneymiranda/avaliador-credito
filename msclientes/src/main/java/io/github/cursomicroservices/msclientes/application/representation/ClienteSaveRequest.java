package io.github.cursomicroservices.msclientes.application.representation;

import io.github.cursomicroservices.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private String idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
