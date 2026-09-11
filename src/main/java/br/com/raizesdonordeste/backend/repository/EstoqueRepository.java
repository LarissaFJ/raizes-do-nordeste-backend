package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
