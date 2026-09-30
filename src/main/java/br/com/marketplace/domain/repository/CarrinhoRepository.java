package br.com.marketplace.domain.repository;

import br.com.marketplace.domain.entity.Carrinho;
import br.com.marketplace.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuario(Usuario usuario);
    Optional<Carrinho> findByUsuarioId(Long usuarioId);
}