package br.com.marketplace.domain.service;

import br.com.marketplace.api.dto.ProdutoRequest;
import br.com.marketplace.api.dto.ProdutoResponse;
import br.com.marketplace.domain.entity.Produto;
import br.com.marketplace.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    // Listar produtos e converter para DTO
    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponse::fromEntity)
                .toList();
    }

    // Buscar por ID devolvendo o DTO
    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return ProdutoResponse.fromEntity(produto);
    }

    // Criar produto a partir do DTO de Request
    @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest request) {
        Produto produto = request.toEntity();
        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoResponse.fromEntity(produtoSalvo);
    }

    // Atualizar produto a partir do DTO de Request
    @Transactional
    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produtoExistente = buscarEntidadePorId(id);

        produtoExistente.setNome(request.nome());
        produtoExistente.setDescricao(request.descricao());
        produtoExistente.setMarca(request.marca());
        produtoExistente.setCategoria(request.categoria());
        produtoExistente.setPreco(request.preco());
        produtoExistente.setEstoque(request.estoque());
        produtoExistente.setImagemUrl(request.imagemUrl());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return ProdutoResponse.fromEntity(produtoAtualizado);
    }

    // Deletar produto (ou inativar se for soft delete)
    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = buscarEntidadePorId(id);
        produtoRepository.delete(produto);
    }

    // Método privado auxiliar para reaproveitar a busca da entidade JPA
    private Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }
}