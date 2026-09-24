package br.com.marketplace.domain.enums;

import lombok.Getter;

@Getter
public enum CategoriaProduto {
    MONITOR("Monitores e Telas"),
    MOUSE("Mouses e Mousepads"),
    TECLADO("Teclados Mecânicos e Membrana"),
    HEADSET("Headsets e Áudio Gamer"),
    PC_GAMER("Computadores e PCs Gamers"),
    HARDWARE("Hardware e Componentes"), // Processador, Placa de Vídeo, Placa-Mãe, RAM
    CADEIRA_GAMER("Cadeiras e Mesas Gamer"),
    ACESSORIOS("Acessórios e Cabos"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }
}
