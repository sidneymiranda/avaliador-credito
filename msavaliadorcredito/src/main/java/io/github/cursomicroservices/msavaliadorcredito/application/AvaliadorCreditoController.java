package io.github.cursomicroservices.msavaliadorcredito.application;

import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.ErroComunicacaoMicroservicesException;
import io.github.cursomicroservices.msavaliadorcredito.application.exceptions.ErroSolicitacaoCartaoException;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import io.github.cursomicroservices.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            var situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

//    @PostMapping("solicitacao-cartao")
//    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
//        try {
//            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = this.avaliadorCreditoService.solicitarEmissaoCartao(dados);
//            return ResponseEntity.ok(protocoloSolicitacaoCartao);
//        } catch (ErroSolicitacaoCartaoException e) {
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//    }
}


