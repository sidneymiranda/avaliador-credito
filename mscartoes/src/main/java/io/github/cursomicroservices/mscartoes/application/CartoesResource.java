package io.github.cursomicroservices.mscartoes.application;


import io.github.cursomicroservices.mscartoes.application.representation.CartaoSaveRequest;
import io.github.cursomicroservices.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.cursomicroservices.mscartoes.domain.Cartao;
import io.github.cursomicroservices.mscartoes.domain.CartaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final CartaoClienteService cartaoClienteService;
    @GetMapping
    public String status() {
        return "Ok";
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@PathParam("renda") Long renda) {
        List<Cartao> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@PathParam("cpf") String cpf) {
        var lista = cartaoClienteService.listCartoesByCpf(cpf);
        var resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
