package com.firstContact.projetoUm.controller;

import com.firstContact.projetoUm.entity.Cart;
import com.firstContact.projetoUm.entity.Dto.request.CartDTO;
import com.firstContact.projetoUm.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private CartService service;

    @PostMapping(value = "/{cartId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable(value = "cartId") Long id, @RequestBody CartDTO dto) {
        Cart cart = service.createCart(id, dto);
        return ResponseEntity.ok().body(cart);

    }

    @GetMapping(value = "/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable(value = "cartId") Long id) {
        Cart cart = service.findCartById(id);
        return ResponseEntity.ok().body(cart);
    }

    @PutMapping(value = "/{cartId}/items")
    public ResponseEntity<Cart> updateItem(
            @PathVariable(value = "cartId") Long id,
            @Valid @RequestBody CartDTO dto) {

        Cart cart = service.updateCart(id, dto);
        return ResponseEntity.ok().body(cart);
    }

    @DeleteMapping(value = "/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeItem(
            @PathVariable(value = "cartId") Long cartId,
            @PathVariable(value = "productId") Long productId) {

        Cart cart = service.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok().body(cart);
    }
}