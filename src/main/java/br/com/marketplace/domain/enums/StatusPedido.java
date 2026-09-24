package br.com.marketplace.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGO("Pago"),
    EM_PROCESSAMENTO("Em Processamento"),
    ENVIADO("Enviado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }
}