package br.com.raizesdonordeste.backend.repository;

import br.com.raizesdonordeste.backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
