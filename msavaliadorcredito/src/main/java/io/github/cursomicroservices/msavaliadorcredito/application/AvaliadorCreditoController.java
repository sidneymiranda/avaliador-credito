package io.github.cursomicroservices.msavaliadorcredito.application;

import io.github.cursomicroservices.msavaliadorcredito.application.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SituacaoCliente> consultarSituacaoCliente(@RequestParam("cpf") String cpf) {
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);
    }
}


