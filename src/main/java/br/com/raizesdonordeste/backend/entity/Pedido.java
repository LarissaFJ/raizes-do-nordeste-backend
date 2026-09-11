package br.com.raizesdonordeste.backend.entity;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long unidadeId;

    @Enumerated(EnumType.STRING)
    private CanalPedido canalPedido;

    private LocalDateTime dataPedido;
    private String statusPedido;
    private BigDecimal desconto;
    private BigDecimal valorTotal;

}
