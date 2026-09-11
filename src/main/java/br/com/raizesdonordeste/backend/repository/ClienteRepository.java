package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
