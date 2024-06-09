package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;

public interface ProductService {
    String createProduct(CreateProductDto productDto);
}
