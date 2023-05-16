package io.github.cursomicroservices.mscartoes.application.representation;

import io.github.cursomicroservices.mscartoes.domain.CartaoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse fromModel(CartaoCliente cartaoCliente) {
        return new CartoesPorClienteResponse(
                cartaoCliente.getCartao().getNome(),
                cartaoCliente.getCartao().getBandeira().toString(),
                cartaoCliente.getLimite()
        );
    }
}
