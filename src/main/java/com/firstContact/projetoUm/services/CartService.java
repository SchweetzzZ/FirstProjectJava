package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.Cart;
import com.firstContact.projetoUm.entity.CartItem;
import com.firstContact.projetoUm.entity.Dto.request.CartDTO;
import com.firstContact.projetoUm.entity.Product;
import com.firstContact.projetoUm.repositories.CartItemsRepository;
import com.firstContact.projetoUm.repositories.CartRepository;
import com.firstContact.projetoUm.repositories.ProductRepository;
import com.firstContact.projetoUm.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    public ProductRepository productRepository;

    @Transactional
    public Cart createCart(Long id, CartDTO dto){
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResolutionException("carrinho não encontrado"));
        Product product = productRepository.findById(dto.product_id()).orElseThrow(() -> new ResourceNotFoundException("produto não encontado"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(dto.product_id()))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.quantity());
        } else {
            CartItem item = new CartItem(null, dto.quantity(), product.getPrice(), cart, product);
            cart.getItems().add(new CartItem());
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCart(Long cartId, CartDTO dto){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().equals(dto.product_id()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no carrinho"));

        item.setQuantity(dto.quantity());
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no carrinho"));

        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    public Cart findCartById(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho nao encontrado"));
        return cart;
    }



}
