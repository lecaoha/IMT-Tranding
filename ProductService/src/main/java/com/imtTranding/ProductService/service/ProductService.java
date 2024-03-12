package com.imtTranding.ProductService.service;

import com.imtTranding.ProductService.dto.ProductDTO;
import com.imtTranding.ProductService.model.Product;
import com.imtTranding.ProductService.respositories.ProductRepostitory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepostitory productRepository;
    private final ModelMapper modelMapper;


    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }
    public ProductDTO getProductByName(String name) {
        Product product = productRepository.findByNameProduct(name);
        if (product != null) {
            return convertToDTO(product);
        } else {
            throw new EntityNotFoundException("Product not found with name: " + name);
        }
    }


    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Integer id, ProductDTO updatedProductDTO) {
        Product updatedProduct = convertToEntity(updatedProductDTO);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        existingProduct.setNameProduct(updatedProduct.getNameProduct());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setType(updatedProduct.getType());

        Product savedProduct = productRepository.save(existingProduct);
        return convertToDTO(savedProduct);
    }

    public void deleteProduct(Integer id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productRepository.delete(existingProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getNameProduct(),
                product.getDescription(),
                product.getType(),
                product.getPrice()
        );
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getNameProduct(),
                productDTO.getDescription(),
                productDTO.getType(),
                productDTO.getPrice()
        );
    }
}