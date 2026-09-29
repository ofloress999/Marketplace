package br.com.marketplace.api.dto;

import br.com.marketplace.domain.entity.Produto;
import br.com.marketplace.domain.enums.CategoriaProduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        String marca,
        CategoriaProduto categoria,
        BigDecimal preco,
        Long estoque,
        String imagemUrl,
        Boolean ativo,
        LocalDateTime dataCriacao
) {
    public static ProdutoResponse fromEntity(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getMarca(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getImagemUrl(),
                produto.getAtivo(),
                produto.getDataCriacao()
        );
    }
}