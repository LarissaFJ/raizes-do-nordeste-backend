package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Pedido;
import br.com.raizesdonordeste.backend.enums.CanalPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByCanalPedido(CanalPedido canalPedido);

    List<Pedido> findByClienteIdAndCanalPedido(
            Long clienteId, CanalPedido canalPedido);

}
