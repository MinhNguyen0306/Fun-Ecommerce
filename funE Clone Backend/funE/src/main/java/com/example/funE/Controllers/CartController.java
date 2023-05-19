package com.example.funE.Controllers;

import com.example.funE.Dtos.CartItemDto;
import com.example.funE.Services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("")
    public ResponseEntity<List<CartItemDto>> getAllCartItemOfUser(@RequestParam("userId") Integer userId) {
        List<CartItemDto> cartItemDtoList = cartItemService.getAllCartItemOfUser(userId);
        if(cartItemDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartItemDtoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CartItemDto> createCartItem(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity
    ) {
        CartItemDto createdCartItem = this.cartItemService.createCartItem(userId, productId, quantity);
        if(createdCartItem == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(createdCartItem, HttpStatus.CREATED);
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(
            @PathVariable Integer cartItemId,
            @RequestParam("quantity") Integer quantity
    ) {
        CartItemDto updatedCartItem = this.cartItemService.updateQuantityCartItem(cartItemId, quantity);
        if(updatedCartItem == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer cartItemId) {
        this.cartItemService.deleteCartItem(cartItemId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSizeCart(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(cartItemService.getSizeCart(userId));
    }
}
