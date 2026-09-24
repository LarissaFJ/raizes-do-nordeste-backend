package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    boolean existsByPedidoIdAndTipoOperacao(Long pedidoId, String tipoOperacao);
}
