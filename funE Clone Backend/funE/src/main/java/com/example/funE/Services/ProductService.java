package com.example.funE.Services;

import com.example.funE.Dtos.ProductDto;
import com.example.funE.Utils.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductDto createProduct(ProductDto productDto, MultipartFile files, Integer categoryId, Integer currencyId);

    List<ProductDto> searchProductsByName(String key);

    List<ProductDto> getAllProductOfUser(Integer userId);

    ProductDto getProductById(Integer productId);

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);
}
