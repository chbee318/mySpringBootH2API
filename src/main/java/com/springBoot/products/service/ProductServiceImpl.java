package com.springBoot.products.service;

import com.springBoot.products.exception.ResourceNotFoundException;
import com.springBoot.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import com.springBoot.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productOb = this.productRepository.findById(product.getId());

        if (productOb.isPresent()){
            Product productUpdate = productOb.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setDescription(productUpdate.getDescription());
            productRepository.save(productUpdate);
            return productUpdate;

        }else{
            throw new ResourceNotFoundException("Record Not Found with id: "+product.getId());
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> productOb = this.productRepository.findById(productId);

        if(productOb.isPresent()){
            return productOb.get();
        }else{
            throw new ResourceNotFoundException("Record Not found with id: "+ productId);
        }
    }

    @Override
    public void deleteProduct(long productId) {
        Optional<Product> productOb = this.productRepository.findById(productId);

        if(productOb.isPresent()){
            this.productRepository.delete(productOb.get());
        }else{
            throw new ResourceNotFoundException("Record Not Found with id: "+ productId);
        }
    }
}
