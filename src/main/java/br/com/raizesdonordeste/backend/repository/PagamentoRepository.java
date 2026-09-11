package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
