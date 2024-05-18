package com.boris.product_service.service;
import com.boris.product_service.entity.Product;
import com.boris.product_service.exception.ProductServiceCustomException;
import com.boris.product_service.model.ProductDTO;

import com.boris.product_service.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Override
    public Long addProduct(ProductDTO productRequest) {
        log.info("BORIS Adding Product....");
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        log.info("BORIS getting the object by Id...");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with Id "+id+" wasnt found", "PRODUCT_NOT_FOUND"));

        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);

        return productDTO;

    }

    @Override
    public void reduceQuantity(Long id, Long quantity) {
        log.info("Changing a product quantity in service");
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                                new ProductServiceCustomException("Product with Id "+id+"wasnt found",
                                        "PRODUCT_NOT_FOUND"));
        if (product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product doesnt have sufficient Quantity", "INSUFFICIENT_QUANTITY");
        }else {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            log.info("product quantity changed");
        }


    }


}
