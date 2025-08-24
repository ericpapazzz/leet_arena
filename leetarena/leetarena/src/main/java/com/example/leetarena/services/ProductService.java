package com.example.leetarena.services;

import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.ProductDTO;
import com.example.leetarena.models.Product;
import com.example.leetarena.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Get all products

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Get Product by Id

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product with Id " + id + " not found"));
    }

    //Create a product
    
    public Product createProduct(ProductDTO dto) {
        Product newProduct = new Product();

        if (dto.getProductName() == null || dto.getProductName().isEmpty()) {
            throw new RuntimeException("Invalid product name: Name cannot be null or empty");
        }else{
            newProduct.setProductName(dto.getProductName());
        }

        if (dto.getProductPrice() == null || dto.getProductPrice() <= 0) {
            throw new RuntimeException("Invalid product price: Price must be greater than 0");
        }else{
            newProduct.setProductPrice(dto.getProductPrice());
        }

        if (dto.getProductImg() == null || dto.getProductImg().isEmpty()) {
            throw new RuntimeException("Invalid product image: Image cannot be null or empty");
        }else{
            newProduct.setProductImg(dto.getProductImg());
        }

        if (dto.getProductDescription() == null || dto.getProductDescription().isEmpty()){
            throw new RuntimeException("Invalid product Description: Description cannot be null or empty");
        }else{
            newProduct.setProductDescription(dto.getProductDescription());
        }
        if (dto.getProductTag() == null || dto.getProductTag().isEmpty()){
            throw new RuntimeException("Invalid product Tag: Tag cannot be null or empty");
        }else{
            newProduct.setProductTag(dto.getProductTag());
        }

        if (productRepository.findByProductName(dto.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name " + dto.getProductName() + " already exists");
        }

        return productRepository.save(newProduct);
    }

    // Update the product if it is given new assets or values
public Product updateProduct(Integer id, ProductDTO dto) {
    Product existingProduct = getProductById(id);

    if (dto.getProductName() != null && !dto.getProductName().isEmpty()) {
        productRepository.findByProductName(dto.getProductName()).ifPresent(existing -> {
            if (!existing.getProduct_id().equals(id)){
                throw new RuntimeException("Product with name " + dto.getProductName() + " already exists");
            }
        });
        existingProduct.setProductName(dto.getProductName());
    }

    if (dto.getProductPrice() != null && dto.getProductPrice() > 0) {
        existingProduct.setProductPrice(dto.getProductPrice());
    }
    if (dto.getProductImg() != null && !dto.getProductImg().isEmpty()) {
        existingProduct.setProductImg(dto.getProductImg());
    }
    if (dto.getProductDescription() != null && !dto.getProductDescription().isEmpty()) {
        existingProduct.setProductDescription(dto.getProductDescription());
    }
    if (dto.getProductTag() != null && !dto.getProductTag().isEmpty()) {
        existingProduct.setProductTag(dto.getProductTag());
    }

    return productRepository.save(existingProduct);
}

    // Delete a product by the given id

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product with the Id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

}
