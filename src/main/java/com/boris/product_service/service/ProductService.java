package com.boris.product_service.service;

import com.boris.product_service.model.ProductDTO;


public interface ProductService {

    Long addProduct(ProductDTO productRequest);

    ProductDTO getProductById(Long id);

    void reduceQuantity(Long id, Long quantity);
}
