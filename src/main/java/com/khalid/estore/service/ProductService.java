package com.khalid.estore.service;

import com.khalid.estore.dto.req.ProductRequestDTO;
import com.khalid.estore.dto.resp.ProductResponseDTO;
import com.khalid.estore.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductResponseDTO> getAllProducts(int page, int size);

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws ResourceNotFoundException;

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO) throws ResourceNotFoundException;

    void deleteProduct(Long id) throws ResourceNotFoundException;
}
