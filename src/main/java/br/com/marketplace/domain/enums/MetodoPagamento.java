package br.com.marketplace.domain.enums;

import lombok.Getter;

@Getter
public enum MetodoPagamento {

    PIX("PIX"),
    CARTAO_CREDITO("Cartão de Crédito"),
    BOLETO("Boleto Bancário");

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }
}