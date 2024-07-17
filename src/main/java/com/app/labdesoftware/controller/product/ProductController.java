package com.app.labdesoftware.controller.product;

import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.service.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) throws BadRequestException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> listAllProducts(){
        List<ProductResponse> products = productService.listAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> listProductByCategory(@PathVariable("categoryId")int categoryId) throws BadRequestException {
        List<ProductResponse> produtcs = productService.listProductsByCategory(categoryId);
        return ResponseEntity.ok(produtcs);
    }


    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductResponse>> listProductsByBrand(@PathVariable("brand")String brand){
        List<ProductResponse> products = productService.listProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/inStock")
    public ResponseEntity<List<ProductResponse>> listProductsInStock(){
        List<ProductResponse> products = productService.listProductsInStock();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/outOfStock")
    public ResponseEntity<List<ProductResponse>> listProductsOutOfStock(){
        List<ProductResponse> products = productService.listProductsOutOfStock();
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) throws BadRequestException {
        Product product = productService.create(productRequest);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") int id, @RequestBody ProductRequest productRequest) throws BadRequestException {
        productService.update(id,productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
