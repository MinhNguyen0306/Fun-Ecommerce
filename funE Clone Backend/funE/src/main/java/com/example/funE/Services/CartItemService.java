package com.example.funE.Services;

import com.example.funE.Dtos.CartItemDto;
import com.example.funE.Repositories.CartItemRepo;

import java.util.List;

public interface CartItemService {
    List<CartItemDto> getAllCartItemOfUser(Integer userId);
    CartItemDto createCartItem(Integer userId, Integer productId, Integer quantity);
    CartItemDto updateQuantityCartItem(Integer cartItemId, Integer quantity);
    String deleteCartItem(Integer cartItemId);
    Integer getSizeCart(Integer userId);
}
