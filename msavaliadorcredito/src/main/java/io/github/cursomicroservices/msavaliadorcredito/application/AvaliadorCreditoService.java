package io.github.cursomicroservices.msavaliadorcredito.application;

import io.github.cursomicroservices.msavaliadorcredito.application.domain.model.SituacaoCliente;
import io.github.cursomicroservices.msavaliadorcredito.infra.clients.CartoesResouceClient;
import io.github.cursomicroservices.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteClient;
    private final CartoesResouceClient cartoesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {
        var dadosClienteResponse = clienteClient.dadosCliente(cpf);
        var cartoesResponse = cartoesClient.getCartoesByCliente((cpf));

        return SituacaoCliente.builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }
}
