package br.com.marketplace.domain.repository;

import br.com.marketplace.domain.entity.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {
}