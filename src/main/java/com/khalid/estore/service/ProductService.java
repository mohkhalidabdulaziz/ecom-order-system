package com.khalid.estore.service;

import com.khalid.estore.dto.CustomerDTO;
import com.khalid.estore.dto.ProductDTO;
import com.khalid.estore.entity.Category;
import com.khalid.estore.entity.Customer;
import com.khalid.estore.entity.Product;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.repository.CategoryRepository;
import com.khalid.estore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<ProductDTO> getAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(this::convertToDTO);
    }

    public ProductDTO createProduct(ProductDTO productDTO) throws ResourceNotFoundException {
        // Fetch the category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Map DTO to entity
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);

        // Save the product
        Product savedProduct = productRepository.save(product);

        // Map entity back to DTO
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Update fields
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
