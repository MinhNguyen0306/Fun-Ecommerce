package com.example.funE.Controllers;

import com.example.funE.Dtos.ProductDto;
import com.example.funE.Services.FileService;
import com.example.funE.Services.ProductService;
import com.example.funE.Utils.AppConstants;
import com.example.funE.Utils.FileUtils;
import com.example.funE.Utils.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService  productService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping("")
    public ResponseEntity<ProductResponse> getAllProduct(
        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
    {
        ProductResponse productResponse = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse, productResponse != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestPart("product") ProductDto productDto,
                                                    @RequestParam("media") MultipartFile file,
                                                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                    @RequestParam(value = "currencyId", required = false) Integer currencyId) {
        return new ResponseEntity<>(this.productService.createProduct(productDto, file, categoryId, currencyId), HttpStatus.CREATED);
    }

    @GetMapping(value = "/media/{mediaName}", produces = "video/mp4")
    public void getMediaResource(@PathVariable String mediaName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, mediaName);
        response.setContentType("video/mp4");
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImageResource(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam("key") String key) {
        List<ProductDto> productDtoList = this.productService.searchProductsByName(key);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDto>> getAllProductOfUser(@PathVariable Integer userId) {
        List<ProductDto> productDtoList = this.productService.getAllProductOfUser(userId);
        return ResponseEntity.ok(productDtoList);
    }
}
