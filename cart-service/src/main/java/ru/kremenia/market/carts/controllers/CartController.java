package ru.kremenia.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kremenia.market.api.CartDto;
import ru.kremenia.market.api.StringResponse;
import ru.kremenia.market.carts.convertes.CartConverter;
import ru.kremenia.market.carts.services.CartService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.addToCart(targetUuid, productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.clearCart(targetUuid);
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void removeFromCart(@RequestHeader(name = "username", required = false) String username, String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.remove(targetUuid, id);
    }

    private String getCartUuid(String username, String uuid){
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
