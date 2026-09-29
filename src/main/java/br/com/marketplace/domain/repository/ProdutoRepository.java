package br.com.marketplace.domain.repository;

import br.com.marketplace.domain.entity.Produto;
import br.com.marketplace.domain.enums.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();
    List<Produto> findByCategoriaAndAtivoTrue(CategoriaProduto categoria);
}
