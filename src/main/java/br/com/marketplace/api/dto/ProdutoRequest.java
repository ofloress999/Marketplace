package br.com.marketplace.api.dto;

import br.com.marketplace.domain.enums.CategoriaProduto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
        String nome,

        String descricao,

        String marca,

        @NotNull(message = "A categoria é obrigatória")
        CategoriaProduto categoria,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quantidade em estoque é obrigatória")
        @Min(value = 0, message = "O estoque não pode ser negativo")
        Long estoque,

        String imagemUrl
) {
    public br.com.marketplace.domain.entity.Produto toEntity() {
        return br.com.marketplace.domain.entity.Produto.builder()
                .nome(this.nome)
                .descricao(this.descricao)
                .marca(this.marca)
                .categoria(this.categoria)
                .preco(this.preco)
                .estoque(this.estoque)
                .imagemUrl(this.imagemUrl)
                .ativo(true) // Todo produto novo nasce ativo
                .build();
    }
}