package io.github.cursomicroservices.mscartoes.application;

import io.github.cursomicroservices.mscartoes.domain.CartaoCliente;
import io.github.cursomicroservices.mscartoes.infra.repository.CartaoClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoClienteService {

    private final CartaoClienteRepository cartaoClienteRepository;

    public List<CartaoCliente> listCartoesByCpf(String cpf) {
        return cartaoClienteRepository.findByCpf(cpf);
    }
}
