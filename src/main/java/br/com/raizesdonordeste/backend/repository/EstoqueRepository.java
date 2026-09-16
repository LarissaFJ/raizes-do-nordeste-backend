package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Estoque;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Estoque> findWithLockByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
}
