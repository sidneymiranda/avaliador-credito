package io.github.cursomicroservices.msavaliadorcredito.infra.mqueue;

import io.github.cursomicroservices.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPub {

    private final RabbitTemplate rabbitTemplate;
    @Value("${mq.exchanges.cartoes}")
    private String exchangeCartoes;
    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) {
        rabbitTemplate.convertAndSend(exchangeCartoes, "", dados);
    }

}
