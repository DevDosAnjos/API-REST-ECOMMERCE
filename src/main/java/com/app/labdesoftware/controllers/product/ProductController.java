package com.app.labdesoftware.controllers.product;

import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements com.app.labdesoftware.controllers.product.Product {

    @Autowired
    private ProductService productService;

    public ResponseEntity<Product> get(User user,Integer id) {
        Product product = productService.getProduct(user,id);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<List<ProductResponse>> listAll(User user){
        List<ProductResponse> products = productService.listAllProducts(user);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponse>> listByCategory(User user,Integer categoryId) {
        List<ProductResponse> products = productService.listByCategory(user,categoryId);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponse>> listByPrice(User user,Integer price){
        List<ProductResponse> products = productService.listByPrice(user,price);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponse>> listByBrand(User user,String brand){
        List<ProductResponse> products = productService.listByBrand(user,brand);
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponse>> listInStock(){
        List<ProductResponse> products = productService.listInStock();
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<List<ProductResponse>> listOutOfStock(){
        List<ProductResponse> products = productService.listOutOfStock();
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<Product> create(ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<Product> update(Integer id, ProductRequest productRequest) {
        Product product = productService.updateProduct(id,productRequest);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<Product> delete(Integer id) {
        Product product = productService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }
}
