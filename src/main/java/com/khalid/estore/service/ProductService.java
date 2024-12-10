package com.khalid.estore.service;

import com.khalid.estore.dto.ProductDTO;
import com.khalid.estore.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductDTO> getAllProducts(int page, int size);

    ProductDTO createProduct(ProductDTO productDTO) throws ResourceNotFoundException;

    ProductDTO updateProduct(Long id, ProductDTO productDTO) throws ResourceNotFoundException;

    void deleteProduct(Long id) throws ResourceNotFoundException;



 /*   @Autowired
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
    }*/
}
