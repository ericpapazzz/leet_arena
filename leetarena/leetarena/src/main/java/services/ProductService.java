package services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.ProductRepository;
import models.Product;
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

        if (product.getProduct_name() == null || product.getProduct_name().isEmpty()) {
            throw new RuntimeException("Invalid product name: Name cannot be null or empty");
        }

        if (product.getProduct_price() == null || product.getProduct_price() <= 0) {
            throw new RuntimeException("Invalid product price: Price must be greater than 0");
        }

        if (product.getProduct_img() == null || product.getProduct_img().isEmpty()) {
            throw new RuntimeException("Invalid product image: Image cannot be null or empty");
        }

        if (productRepository.findByProductName(product.getProduct_name()).isPresent()) {
            throw new RuntimeException("Product with name " + product.getProduct_name() + " already exists");
        }

        return productRepository.save(product);
    }

    // Update the product if it is given new assets or values

    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = getProductById(id);

        if (product.getProduct_name() != null && !product.getProduct_name().isEmpty()){
            productRepository.findByProductName(product.getProduct_name()).ifPresent(existing -> {
                if (!existing.getProduct_id().equals(id)){
                    throw new RuntimeException("Product with name " + product.getProduct_name() + " already exists");
                }
            });
        }
        if (product.getProduct_price() != null && product.getProduct_price() > 0) {
            existingProduct.setProduct_price(product.getProduct_price());
        }
        if (product.getProduct_img() != null && !product.getProduct_img().isEmpty()) {
            existingProduct.setProduct_img(product.getProduct_img());
        }
        if (product.getProduct_description() != null && !product.getProduct_description().isEmpty()) {
            existingProduct.setProduct_description(product.getProduct_description());
        }
        if (product.getProduct_tag() != null && !product.getProduct_tag().isEmpty()) {
            existingProduct.setProduct_tag(product.getProduct_tag());
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
