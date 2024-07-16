package com.app.labdesoftware.controller.cart;

import com.app.labdesoftware.entities.Cart;
import com.app.labdesoftware.service.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable("id") int id) {
        CartResponse cart = cartService.findById(id);
        return ResponseEntity.ok(cart);
    }

   @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> listAll(){
       List<CartResponse> carts = cartService.findAll();
       return ResponseEntity.ok(carts);
   }


    @PostMapping()
    public ResponseEntity<Cart> createCart(@RequestBody CartRequest cartRequest) {
        Cart cart = cartService.create(cartRequest);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCart(@PathVariable("id") int id, @RequestBody CartRequest cartRequest) throws BadRequestException {
        cartService.update(id, cartRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@PathVariable("id") int id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
