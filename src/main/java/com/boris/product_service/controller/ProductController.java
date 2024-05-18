package com.boris.product_service.controller;

import com.boris.product_service.model.ProductDTO;

import com.boris.product_service.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Log4j2
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create_product")
    public ResponseEntity<Long> addProduct(@RequestBody ProductDTO productRequest){
        log.info("BORIS Controller creating product...");
        Long productId = productService.addProduct(productRequest);

        return new ResponseEntity<>( productId, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        ProductDTO productResponse = productService.getProductById(id);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("prod/{id}")
    public ProductDTO getProductRest(@PathVariable Long id){
        ProductDTO productResponse = productService.getProductById(id);
        System.out.println(productResponse.getProductId());
        System.out.println("priiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiint");

        return productResponse;
    }

    @PostMapping("/reducequantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long id, @RequestBody Map<String, Long> request){
        log.info("starting to change product quantity in controller");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        Long quantity = request.get("quantity");
        if (quantity == null) {
            // Handle the case where the quantity is not provided in the request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.reduceQuantity(id, quantity);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
