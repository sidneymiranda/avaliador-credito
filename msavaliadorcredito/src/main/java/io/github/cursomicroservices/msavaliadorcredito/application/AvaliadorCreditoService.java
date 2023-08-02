package io.github.cursomicroservices.msavaliadorcredito.application;

import feign.FeignException;
import io.github.cursomicroservices.msavaliadorcredito.application.domain.model.DadosCliente;
import io.github.cursomicroservices.msavaliadorcredito.application.domain.model.SituacaoCliente;
import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.ErroComunicacaoMicroservicesException;
import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.ErroSolicitacaoCartaoException;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.*;
import io.github.cursomicroservices.msavaliadorcredito.infra.clients.CartoesResouceClient;
import io.github.cursomicroservices.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.cursomicroservices.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPub;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteClient;
    private final CartoesResouceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPub emissaoCartaoPub;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            var dadosClienteResponse = clienteClient.dadosCliente(cpf);
            var cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException ex){
            int status = ex.status();
            if(status == HttpStatus.NOT_FOUND.value()) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            List<CartaoAprovado> cartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(cartoesAprovados);

        } catch (FeignException.FeignClientException ex) {
            int status = ex.status();
            if(status == HttpStatus.NOT_FOUND.value()) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            emissaoCartaoPub.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
