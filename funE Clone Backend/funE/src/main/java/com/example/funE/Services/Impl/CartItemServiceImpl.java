package com.example.funE.Services.Impl;

import com.example.funE.Dtos.CartItemDto;
import com.example.funE.Entities.CartItem;
import com.example.funE.Entities.Product;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.CartItemRepo;
import com.example.funE.Repositories.ProductRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CartItemDto> getAllCartItemOfUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        List<CartItem> cartItemList = user.getCartItems();
        List<CartItemDto> cartItemDtoList = cartItemList.stream()
                .map(cartItem -> this.modelMapper.map(cartItem, CartItemDto.class)).collect(Collectors.toList());
        return cartItemDtoList;
    }

    @Override
    public CartItemDto createCartItem(Integer userId, Integer productId, Integer quantity) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        if(product.getStock() == 0) {
            return null;
        }
        List<CartItem> cartItemList = this.cartItemRepo.findAll();
        Optional<CartItem> cartItemOptional = cartItemList.stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productId).findFirst();
        CartItem cartItem = cartItemOptional.isPresent() ? cartItemOptional.get() : new CartItem();
        Integer product_stock = product.getStock();
        if(cartItem.getProduct() == null) {
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }

        if(product_stock < quantity) {
            cartItem.setQuantity(product_stock);
        } else {
            cartItem.setQuantity(quantity);
        }
        CartItem savedCartItem = this.cartItemRepo.save(cartItem);
        return modelMapper.map(savedCartItem, CartItemDto.class);
    }

    @Override
    public CartItemDto updateQuantityCartItem(Integer cartItemId, Integer quantity) {
        CartItem cartItem = this.cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
        Integer product_stock = cartItem.getProduct().getStock();
        if(quantity == 0) {
            cartItemRepo.delete(cartItem);
            return null;
        }
        if(quantity > product_stock) {
            cartItem.setQuantity(product_stock);
        }
        cartItem.setQuantity(quantity);
        CartItem updatedCartItem = this.cartItemRepo.save(cartItem);
        return modelMapper.map(updatedCartItem, CartItemDto.class);
    }

    @Override
    public String deleteCartItem(Integer cartItemId) {
        CartItem cartItem = this.cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
        this.cartItemRepo.delete(cartItem);
        return "success";
    }

    @Override
    public Integer getSizeCart(Integer userId) {
        return cartItemRepo.getSizeCart(userId);
    }
}
