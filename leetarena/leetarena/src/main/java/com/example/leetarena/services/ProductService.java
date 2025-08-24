package com.example.leetarena.services;

import org.springframework.stereotype.Service;

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
    
    public Product createProduct(Product product) {

        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new RuntimeException("Invalid product name: Name cannot be null or empty");
        }

        if (product.getProductPrice() == null || product.getProductPrice() <= 0) {
            throw new RuntimeException("Invalid product price: Price must be greater than 0");
        }

        if (product.getProductImg() == null || product.getProductImg().isEmpty()) {
            throw new RuntimeException("Invalid product image: Image cannot be null or empty");
        }

        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name " + product.getProductName() + " already exists");
        }

        return productRepository.save(product);
    }

    // Update the product if it is given new assets or values

    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = getProductById(id);

        if (product.getProductName() != null && !product.getProductName().isEmpty()){
            productRepository.findByProductName(product.getProductName()).ifPresent(existing -> {
                if (!existing.getProduct_id().equals(id)){
                    throw new RuntimeException("Product with name " + product.getProductName() + " already exists");
                }
            });
        }
        if (product.getProductPrice() != null && product.getProductPrice() > 0) {
            existingProduct.setProductPrice(product.getProductPrice());
        }
        if (product.getProductImg() != null && !product.getProductImg().isEmpty()) {
            existingProduct.setProductImg(product.getProductImg());
        }
        if (product.getProductDescription() != null && !product.getProductDescription().isEmpty()) {
            existingProduct.setProductDescription(product.getProductDescription());
        }
        if (product.getProductTag() != null && !product.getProductTag().isEmpty()) {
            existingProduct.setProductTag(product.getProductTag());
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
