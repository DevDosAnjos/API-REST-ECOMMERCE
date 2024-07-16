package com.app.labdesoftware.service;

import com.app.labdesoftware.controller.cart.CartRequest;
import com.app.labdesoftware.controller.cart.CartResponse;
import com.app.labdesoftware.entities.Cart;
import com.app.labdesoftware.enumeration.StatusCart;
import com.app.labdesoftware.repository.CartRepository;
import com.app.labdesoftware.repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;


    public CartResponse findById(int id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()){
            return new CartResponse(
                    cart.get().getId(),
                    cart.get().getClientId(),
                    cart.get().getOrders(),
                    cart.get().getStatusCart()
            );
        }
        return null;
    }

    public List<CartResponse> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return CartResponse.fromCart(carts);
    }

    public Cart create(CartRequest cartRequest) {
        Cart cart = Cart.from(cartRequest,orderRepository);
        return cartRepository.save(cart);
    }

    public void update(int id, CartRequest cartRequest) throws BadRequestException {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()) {
            throw new BadRequestException("CART IS NOT FOUND");
        }
        cartRepository.saveAndFlush(cart.get());
    }

    public void delete(int id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            cart.get().setStatusCart(StatusCart.CANCELED);
        }
    }
}
