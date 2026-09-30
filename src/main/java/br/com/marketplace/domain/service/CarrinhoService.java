package br.com.marketplace.domain.service;

import br.com.marketplace.api.dto.AdicionarItemRequest;
import br.com.marketplace.api.dto.CarrinhoResponse;
import br.com.marketplace.domain.entity.Carrinho;
import br.com.marketplace.domain.entity.CarrinhoItem;
import br.com.marketplace.domain.entity.Produto;
import br.com.marketplace.domain.entity.Usuario;
import br.com.marketplace.domain.repository.CarrinhoRepository;
import br.com.marketplace.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public CarrinhoResponse obterCarrinhoDoUsuario(Usuario usuario) {
        Carrinho carrinho = buscarOuCriarCarrinho(usuario);
        return CarrinhoResponse.fromEntity(carrinho);
    }

    @Transactional
    public CarrinhoResponse adicionarItem(Usuario usuario, AdicionarItemRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (!produto.getAtivo()) {
            throw new IllegalArgumentException("O produto " + produto.getNome() + " não está disponível para venda");
        }

        Carrinho carrinho = buscarOuCriarCarrinho(usuario);

        Optional<CarrinhoItem> itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produto.getId()))
                .findFirst();

        int quantidadeFinal = request.quantidade();
        if (itemExistente.isPresent()) {
            quantidadeFinal += itemExistente.get().getQuantidade();
        }

        if (quantidadeFinal > produto.getEstoque()) {
            throw new IllegalArgumentException("Estoque insuficiente. Estoque disponível: " + produto.getEstoque());
        }

        if (itemExistente.isPresent()) {
            itemExistente.get().setQuantidade(quantidadeFinal);
        } else {
            CarrinhoItem novoItem = CarrinhoItem.builder()
                    .carrinho(carrinho)
                    .produto(produto)
                    .quantidade(request.quantidade())
                    .precoUnitario(produto.getPreco())
                    .build();
            carrinho.getItens().add(novoItem);
        }

        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
        return CarrinhoResponse.fromEntity(carrinhoSalvo);
    }

    @Transactional
    public CarrinhoResponse atualizarQuantidadeItem(Usuario usuario, Long itemId, Integer novaQuantidade) {
        if (novaQuantidade <= 0) {
            return removerItem(usuario, itemId);
        }

        Carrinho carrinho = buscarOuCriarCarrinho(usuario);
        CarrinhoItem item = carrinho.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado no carrinho"));

        if (novaQuantidade > item.getProduto().getEstoque()) {
            throw new IllegalArgumentException("Estoque insuficiente. Estoque disponível: " + item.getProduto().getEstoque());
        }

        item.setQuantidade(novaQuantidade);
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
        return CarrinhoResponse.fromEntity(carrinhoSalvo);
    }

    @Transactional
    public CarrinhoResponse removerItem(Usuario usuario, Long itemId) {
        Carrinho carrinho = buscarOuCriarCarrinho(usuario);
        boolean removido = carrinho.getItens().removeIf(item -> item.getId().equals(itemId));

        if (!removido) {
            throw new IllegalArgumentException("Item não encontrado no carrinho");
        }

        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
        return CarrinhoResponse.fromEntity(carrinhoSalvo);
    }

    private Carrinho buscarOuCriarCarrinho(Usuario usuario) {
        return carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = Carrinho.builder()
                            .usuario(usuario)
                            .build();
                    return carrinhoRepository.save(novoCarrinho);
                });
    }
}