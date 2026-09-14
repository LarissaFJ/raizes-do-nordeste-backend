package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
