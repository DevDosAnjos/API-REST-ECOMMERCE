package com.app.labdesoftware.service;

import com.app.labdesoftware.controller.product.ProductRequest;
import com.app.labdesoftware.controller.product.ProductResponse;
import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.enumeration.Stock;
import com.app.labdesoftware.repository.CategoryRepository;
import com.app.labdesoftware.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product getProductById(int id) throws BadRequestException {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            throw new BadRequestException("PRODUCT ID NOT FOUND");
        }
        return product.get();
    }

    public List<ProductResponse> listAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listProductsByCategory(int categoryId) throws BadRequestException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            throw new BadRequestException("CATEGORY ID NOT FOUND");
        }
        List<Product> products = productRepository.findByCategory(category.get());
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listProductsInStock() {
        List<Product> products = productRepository.findByStock(Stock.IN_STOCK);
        return ProductResponse.fromProduct(products);
    }
    
    public List<ProductResponse> listProductsOutOfStock() {
        List<Product> products = productRepository.findByStock(Stock.OUT_OF_STOCK);
        return ProductResponse.fromProduct(products);
    }

    public Product create(ProductRequest productRequest) throws BadRequestException {
        Optional<Category> category = categoryRepository.findById(productRequest.categoryID());
        if (!category.isPresent()){
            throw new BadRequestException("CATEGORY ID NOT FOUND");
        }
       Product product = Product.from(productRequest,category.get());
        product.setStock(Stock.IN_STOCK);
        return productRepository.save(product);
    }

    public void update(int id, ProductRequest productRequest) throws BadRequestException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new BadRequestException("PRODUCT ID NOT FOUND");
        }
        if (productRequest.name() != null) {
            product.get().setName(productRequest.name());
        }
        if(productRequest.brand() != null){
            product.get().setBrand(productRequest.brand());
        }
        if (productRequest.price() != null) {
            product.get().setPrice(productRequest.price());
        }
        productRepository.saveAndFlush(product.get());
    }

    public void delete(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setStock(Stock.OUT_OF_STOCK);
            productRepository.saveAndFlush(product.get());
        }
    }

}
