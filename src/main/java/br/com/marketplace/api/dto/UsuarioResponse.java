package br.com.marketplace.api.dto;

import br.com.marketplace.domain.entity.Usuario;
import br.com.marketplace.domain.enums.UserRole;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        UserRole role,
        LocalDateTime dataCriacao
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getDataCriacao()
        );
    }
}