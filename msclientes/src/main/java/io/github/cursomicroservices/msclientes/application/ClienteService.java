package io.github.cursomicroservices.msclientes.application;

import io.github.cursomicroservices.msclientes.domain.Cliente;
import io.github.cursomicroservices.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> getByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
