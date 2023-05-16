package io.github.cursomicroservices.msclientes.infra.repository;

import io.github.cursomicroservices.msclientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
}
