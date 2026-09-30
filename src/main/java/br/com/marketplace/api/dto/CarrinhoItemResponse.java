package br.com.marketplace.api.dto;

import br.com.marketplace.domain.entity.CarrinhoItem;

import java.math.BigDecimal;

public record CarrinhoItemResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
    public static CarrinhoItemResponse fromEntity(CarrinhoItem item) {
        return new CarrinhoItemResponse(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }
}