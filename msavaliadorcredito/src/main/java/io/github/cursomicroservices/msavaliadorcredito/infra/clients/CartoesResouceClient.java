package io.github.cursomicroservices.msavaliadorcredito.infra.clients;

import io.github.cursomicroservices.msavaliadorcredito.application.domain.model.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.PathParam;
import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResouceClient {
    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@PathParam("cpf") String cpf);
}
