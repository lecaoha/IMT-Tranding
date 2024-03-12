package com.imtTranding.ProductService.respositories;

import com.imtTranding.ProductService.dto.ProductDTO;
import com.imtTranding.ProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepostitory extends JpaRepository<Product, Integer> {
    Product findByNameProduct(String name);

}
