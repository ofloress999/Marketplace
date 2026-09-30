package br.com.marketplace.api.controller;

import br.com.marketplace.api.dto.AdicionarItemRequest;
import br.com.marketplace.api.dto.CarrinhoResponse;
import br.com.marketplace.domain.entity.Usuario;
import br.com.marketplace.domain.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponse> obterCarrinho(@AuthenticationPrincipal Usuario usuario) {
        CarrinhoResponse carrinho = carrinhoService.obterCarrinhoDoUsuario(usuario);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponse> adicionarItem(
            @AuthenticationPrincipal Usuario usuario,
            @Valid @RequestBody AdicionarItemRequest request) {
        CarrinhoResponse carrinho = carrinhoService.adicionarItem(usuario, request);
        return ResponseEntity.ok(carrinho);
    }

    @PutMapping("/itens/{itemId}")
    public ResponseEntity<CarrinhoResponse> atualizarQuantidade(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long itemId,
            @RequestParam Integer quantidade) {
        CarrinhoResponse carrinho = carrinhoService.atualizarQuantidadeItem(usuario, itemId, quantidade);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<CarrinhoResponse> removerItem(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long itemId) {
        CarrinhoResponse carrinho = carrinhoService.removerItem(usuario, itemId);
        return ResponseEntity.ok(carrinho);
    }
}