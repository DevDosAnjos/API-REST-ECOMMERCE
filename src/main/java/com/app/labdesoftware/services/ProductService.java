package com.app.labdesoftware.services;

import com.app.labdesoftware.controllers.product.ProductRequest;
import com.app.labdesoftware.controllers.product.ProductResponse;
import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.StatusCategory;
import com.app.labdesoftware.enumerations.StatusStock;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.CategoryRepository;
import com.app.labdesoftware.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product getProduct(User user, Integer id)  {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"PRODUCT ID NOT FOUND");
        }
        if (user.getRole().equals(UserRole.USER) && product.get().getStatusStock() == StatusStock.OUT_OF_STOCK) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "PRODUCT OUT OF STOCK, ENTER ANOTHER ID ");
        }
        return product.get();
    }

    public List<ProductResponse> listAllProducts(User user) {
        List<Product> products;
        if (user.getRole().equals(UserRole.USER)){
            products = productRepository.findAllByStatusStock(StatusStock.IN_STOCK);
        } else {
            products = productRepository.findAll();
        }
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listByCategory(User user,Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY ID NOT FOUND");
        }
        List<Product> products;
        if (user.getRole().equals(UserRole.USER)){
            products = productRepository.findAllByCategoryAndStatusStock(category,StatusStock.IN_STOCK);
        } else {
            products = productRepository.findAllByCategory(category);
        }
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listByPrice(User user,Integer price) {
        List<Product> products;
        if (user.getRole().equals(UserRole.USER)){
            products = productRepository.findAllByPriceAndStatusStock(price,StatusStock.IN_STOCK);
        } else {
            products = productRepository.findAllByPrice(price);
        }
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listByBrand(User user,String brand) {
        List<Product> products;
        if (user.getRole().equals(UserRole.USER)){
            products = productRepository.findAllByBrandAndStatusStock(brand,StatusStock.IN_STOCK);
        } else {
            products = productRepository.findAllByBrand(brand);
        }
        return ProductResponse.fromProduct(products);
    }

    public List<ProductResponse> listInStock() {
        List<Product> products = productRepository.findAllByStatusStock(StatusStock.IN_STOCK);
        return ProductResponse.fromProduct(products);
    }
    
    public List<ProductResponse> listOutOfStock() {
        List<Product> products = productRepository.findAllByStatusStock(StatusStock.OUT_OF_STOCK);
        return ProductResponse.fromProduct(products);
    }

    public Product createProduct(ProductRequest productRequest) {
        Optional<Category> category = categoryRepository.findById(productRequest.categoryID());
        if (category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY ID NOT FOUND");
        }
        if (category.get().getStatusCategory().equals(StatusCategory.INACTIVE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CATEGORY IS INACTIVE");
        }
       Product product = Product.from(productRequest,category.get());
        product.setStatusStock(StatusStock.IN_STOCK);
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"PRODUCT ID NOT FOUND");
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
        if (productRequest.categoryID() != null){
            Optional<Category> category = categoryRepository.findById(productRequest.categoryID());
            if (category.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY ID NOT FOUND");
            }
            if (category.get().getStatusCategory().equals(StatusCategory.INACTIVE)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CATEGORY IS INACTIVE");
            }
            product.get().setCategory(category.get());
        }
        if (productRequest.statusStock() != null ){
           product.get().setStatusStock(productRequest.statusStock());
        }
        return productRepository.saveAndFlush(product.get());
    }

    public Product deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"PRODUCT NOT FOUND, ID IS INCORRECT");
        }
        product.get().setStatusStock(StatusStock.OUT_OF_STOCK);
        return productRepository.saveAndFlush(product.get());
    }
}
