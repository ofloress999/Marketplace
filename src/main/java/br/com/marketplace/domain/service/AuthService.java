package br.com.marketplace.domain.service;

import br.com.marketplace.api.dto.LoginRequest;
import br.com.marketplace.api.dto.LoginResponse;
import br.com.marketplace.api.dto.RegisterRequest;
import br.com.marketplace.api.dto.UsuarioResponse;
import br.com.marketplace.config.TokenService;
import br.com.marketplace.domain.entity.Usuario;
import br.com.marketplace.domain.enums.UserRole;
import br.com.marketplace.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public UsuarioResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Define ROLE_USER como padrão se não for informada
        UserRole userRole = (request.role() != null) ? request.role() : UserRole.USER;

        // Codifica a senha simples em hash BCrypt
        String senhaCriptografada = passwordEncoder.encode(request.senha());

        Usuario novoUsuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(senhaCriptografada)
                .role(userRole)
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return UsuarioResponse.fromEntity(usuarioSalvo);
    }

    public LoginResponse login(LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.senha()
        );

        var authentication = authenticationManager.authenticate(authToken);

        var usuario = (Usuario) authentication.getPrincipal();

        var token = tokenService.gerarToken(usuario);

        return new LoginResponse(token);
    }


}