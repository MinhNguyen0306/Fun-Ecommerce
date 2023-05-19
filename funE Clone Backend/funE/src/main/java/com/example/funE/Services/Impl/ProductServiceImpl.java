package com.example.funE.Services.Impl;

import com.example.funE.Dtos.AudioDto;
import com.example.funE.Dtos.PhotoDto;
import com.example.funE.Dtos.ProductDto;
import com.example.funE.Dtos.VideoDto;
import com.example.funE.Entities.*;
import com.example.funE.Entities.Currency;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.*;
import com.example.funE.Services.FileService;
import com.example.funE.Services.ProductService;
import com.example.funE.Utils.AppConstants;
import com.example.funE.Utils.FileUtils;
import com.example.funE.Utils.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private PhotoRepo photoRepo;
    @Autowired
    private AudioRepo audioRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CurrencyRepo currencyRepo;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepo.findAll(page);

        List<Product> products = productPage.getContent();
        List<ProductDto> productDtos = products.stream()
                .map(product -> this.productToDto(product)).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageNumber);
        productResponse.setPageSize(pageSize);
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());

        return productResponse;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile, Integer categoryId, Integer currencyId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        Currency currency = this.currencyRepo.findById(currencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Currency", "Id", currencyId));
        Product product = dtoToProduct(productDto);
        product.setCurrency(currency);
        product.setCategory(category);
        Product savedProduct = this.productRepo.save(product);
//        Stream.of(files).forEach(multipartFile -> {
            try {
                String fileName = fileService.uploadImage(path, multipartFile);
                Optional<String> extensionOptional = FileUtils.getExtensionByStringHandling(fileName);

                if(extensionOptional.isPresent()) {
                    String extension = extensionOptional.get();
                    switch (extension) {
                        case "mp4":
                            VideoDto videoDto = new VideoDto();
                            videoDto.setVideoName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            videoDto.setVideoUrl(fileName);
                            Video videoSaved = dtoToVideo(videoDto);
                            videoSaved.setProduct(savedProduct);
                            videoRepo.save(videoSaved);
                            break;
                        case "mp3":
                            AudioDto audioDto = new AudioDto();
                            audioDto.setAudioName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            audioDto.setAudioUrl(fileName);
                            Audio audioSaved = dtoToAudio(audioDto);
                            audioSaved.setProduct(savedProduct);
                            audioRepo.save(audioSaved);
                            break;
                        default:
                            PhotoDto photoDto = new PhotoDto();
                            photoDto.setPhotoName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            photoDto.setPhotoUrl(fileName);
                            Photo photoSaved = dtoToPhoto(photoDto);
                            photoSaved.setProduct(savedProduct);
                            photoRepo.save(photoSaved);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            };
        return this.productToDto(savedProduct);
    }

    @Override
    public List<ProductDto> searchProductsByName(String key) {
        List<Product> productList = this.productRepo.searchProductByName("%"+key+"%");
        System.out.println(productList);
        List<ProductDto> productDtoList = productList.stream().map(product -> productToDto(product)).collect(Collectors.toList());
        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllProductOfUser(Integer userId) {
        List<Product> allProduct = this.productRepo.findAll();
        List<Product> productsOfUser = allProduct.stream().
                filter(product -> product.getUser().getId() == userId).collect(Collectors.toList());
        List<ProductDto> productDtoList = productsOfUser.stream()
                .map(product -> productToDto(product)).collect(Collectors.toList());
        return productDtoList;
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
        return this.productToDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPricing(productDto.getPricing());
        product.setStock(productDto.getStock());
//        product.setCategory(productDto.getCategory());
//        product.setCurrency(productDto.getCurrency());

        Product updatedProduct = this.productRepo.save(product);
        return this.productToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        this.productRepo.delete(product);
    }

    private Product dtoToProduct(ProductDto productDto) {
        return this.modelMapper.map(productDto, Product.class);
    }

    private ProductDto productToDto(Product product) {
        return this.modelMapper.map(product, ProductDto.class);
    }

    private Video dtoToVideo(VideoDto videoDto) {
        return this.modelMapper.map(videoDto, Video.class);
    }

    private Audio dtoToAudio(AudioDto audioDto) {
        return this.modelMapper.map(audioDto, Audio.class);
    }

    private Photo dtoToPhoto(PhotoDto photoDto) {
        return this.modelMapper.map(photoDto, Photo.class);
    }
}
