package com.imtTranding.ProductService.controller;

import com.imtTranding.ProductService.dto.ProductDTO;
import com.imtTranding.ProductService.model.Product;
import com.imtTranding.ProductService.respositories.ProductRepostitory;
import com.imtTranding.ProductService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class productController {
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public productController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllPosts() {
        return productService.getAllProducts().stream().map(post -> modelMapper.map(post, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
        try {
            if (id != null) {
                // Search by ID
                ProductDTO productDTO = productService.getProductById(id);
                return ResponseEntity.ok(productDTO);
            } else if (name != null) {
                // Search by Name
                ProductDTO productDTO = productService.getProductByName(name);
                if (productDTO != null) {
                    return ResponseEntity.ok(productDTO);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                // No search criteria provided
                return ResponseEntity.badRequest().body("Please provide either 'id' or 'name' parameter for search");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);
        return ResponseEntity.ok(savedProductDTO);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO updatedProductDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, updatedProductDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


}