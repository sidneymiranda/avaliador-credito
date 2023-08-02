package io.github.cursomicroservices.msavaliadorcredito.application;

import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.ErroSolicitacaoCartaoException;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("solicitacao-cartao")
@RequiredArgsConstructor
public class SolicitacaoCartaoControler {

    private final AvaliadorCreditoService avaliadorCreditoService;
    @PostMapping
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = this.avaliadorCreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
