package io.github.cursomicroservices.mscartoes.infra.repository.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cursomicroservices.mscartoes.domain.Cartao;
import io.github.cursomicroservices.mscartoes.domain.CartaoCliente;
import io.github.cursomicroservices.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.cursomicroservices.mscartoes.infra.repository.CartaoClienteRepository;
import io.github.cursomicroservices.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final CartaoClienteRepository cartaoClienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            CartaoCliente cartaoCliente = new CartaoCliente();
            cartaoCliente.setCartao(cartao);
            cartaoCliente.setCpf(dados.getCpf());
            cartaoCliente.setLimite(dados.getLimiteLiberado());

            cartaoClienteRepository.save(cartaoCliente);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
