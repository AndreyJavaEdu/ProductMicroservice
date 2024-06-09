package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public String createProduct(CreateProductDto productDto) {
        return "";
    }
}
