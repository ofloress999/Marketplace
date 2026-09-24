package br.com.marketplace.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPagamento {

    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    RECUSADO("Recusado"),
    REEMBOLSADO("Reembolsado");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
}