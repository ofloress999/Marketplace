package br.com.marketplace.api.dto;

import br.com.marketplace.domain.entity.Carrinho;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoResponse(
        Long id,
        Long usuarioId,
        List<CarrinhoItemResponse> itens,
        BigDecimal valorTotal
) {
    public static CarrinhoResponse fromEntity(Carrinho carrinho) {
        List<CarrinhoItemResponse> itensResponse = carrinho.getItens().stream()
                .map(CarrinhoItemResponse::fromEntity)
                .toList();

        return new CarrinhoResponse(
                carrinho.getId(),
                carrinho.getUsuario().getId(),
                itensResponse,
                carrinho.getValorTotal()
        );
    }
}