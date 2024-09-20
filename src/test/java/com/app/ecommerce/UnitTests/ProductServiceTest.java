package com.app.ecommerce.UnitTests;

import com.app.ecommerce.controllers.product.ProductRequest;
import com.app.ecommerce.controllers.product.ProductResponse;
import com.app.ecommerce.entities.Category;
import com.app.ecommerce.entities.Product;
import com.app.ecommerce.entities.User;
import com.app.ecommerce.enumerations.StatusCategory;
import com.app.ecommerce.enumerations.StatusStock;
import com.app.ecommerce.enumerations.UserRole;
import com.app.ecommerce.repositories.CategoryRepository;
import com.app.ecommerce.repositories.ProductRepository;
import com.app.ecommerce.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should get a product")
    void getProduct() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Product response = productService.getProduct(user,product.getId());

        Assertions.assertEquals(product,response);
    }

    @Test
    @DisplayName("Verify if the product exists, if not throws an exception")
    void getProductException() {
        User user = new User("User","User", UserRole.USER);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.getProduct(user,anyInt()));
    }

    @Test
    @DisplayName("Verity if authenticated user has role User and if product is out of stock, and throws a exception")
    void getProductException2() {
        Category category = new Category("Test");
        User user = new User("User","User", UserRole.USER);
        Product product = new Product("Product Test", "Test", 10, category);

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.getProduct(user,product.getId()));
    }

    @Test
    @DisplayName("Should return a list of all products in stock")
    void listAllProductsUser() {
        User user = new User("User","User", UserRole.USER);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByStatusStock(StatusStock.IN_STOCK)).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listAllProducts(user);

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products")
    void listAllProductsAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAll()).thenReturn(List.of(product,product2,product3,product4));
        List<ProductResponse> products = productService.listAllProducts(user);

        Assertions.assertEquals(4,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products in stock by category")
    void listByCategoryUser() {
        User user = new User("User","User", UserRole.USER);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategoryAndStatusStock(any(),any())).thenReturn(List.of(product,product2,product3,product4));
        List<ProductResponse> products = productService.listByCategory(user,category.getId());

        Assertions.assertEquals(4,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products by category")
    void listByCategoryAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategory(any())).thenReturn(List.of(product,product2,product3,product4));
        List<ProductResponse> products = productService.listByCategory(user,category.getId());

        Assertions.assertEquals(4,products.size());
    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void listByCategoryException() {
        User user = new User("Admin","Admin", UserRole.ADMIN);

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.listByCategory(user,anyInt()));
    }

    @Test
    @DisplayName("Should return a list of all products in stock by price")
    void listByPriceUser() {
        User user = new User("User","User", UserRole.USER);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 10, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByPriceAndStatusStock(10,StatusStock.IN_STOCK)).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listByPrice(user,10);

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products by price")
    void listByPriceAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 10, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByPrice(10)).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listByPrice(user,10);

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products in stock by brand")
    void listByBrandUser() {
        User user = new User("User","User", UserRole.USER);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByBrandAndStatusStock("Test",StatusStock.IN_STOCK)).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listByBrand(user,"Test");

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should return a list of all products by brand")
    void listByBrandAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByBrand("Test")).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listByBrand(user,"Test");

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should get a list all product in stock")
    void listInStock() {
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByStatusStock(StatusStock.IN_STOCK)).thenReturn(List.of(product,product3));
        List<ProductResponse> products = productService.listInStock();

        Assertions.assertEquals(2,products.size());
    }

    @Test
    @DisplayName("Should get a list all products out of stock")
    void listOutOfStock() {
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        Product product2 = new Product("Product Test2", "Test2", 20, category);
        Product product3 = new Product("Product Test3", "Test3", 30, category);
        Product product4 = new Product("Product Test4", "Test4", 40, category);
        product2.setStatusStock(StatusStock.OUT_OF_STOCK);
        product4.setStatusStock(StatusStock.OUT_OF_STOCK);

        when(productRepository.findAllByStatusStock(StatusStock.OUT_OF_STOCK)).thenReturn(List.of(product2,product4));
        List<ProductResponse> products = productService.listOutOfStock();

        Assertions.assertEquals(2,products.size());
    }


    @Test
    @DisplayName("Should create a new product")
    void createProduct() {
        Category category = new Category("Test");
        ProductRequest productRequest = new ProductRequest("Product Create Test", "Test Create",10,category.getId(),StatusStock.IN_STOCK);
        Product product = Product.from(productRequest,category);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(product);
        Product response = productService.createProduct(productRequest);

        Assertions.assertEquals(product,response);
    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void createProductException() {
        ProductRequest productRequest = new ProductRequest("Product Create Test", "Test Create",10,1, null);

        when(categoryRepository.findById(productRequest.categoryID())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.createProduct(productRequest));
    }

    @Test
    @DisplayName("Verify if the existing category is inactive, if it is, it throws an exception")
    void createProductException2() {
        Category category = new Category("Test");
        category.setStatusCategory(StatusCategory.INACTIVE);
        ProductRequest productRequest = new ProductRequest("Product Create Test", "Test Create",10,category.getId(),StatusStock.IN_STOCK);

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.createProduct(productRequest));
    }

    @Test
    @DisplayName("Should update a product")
    void updateProduct() {
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        product.setStatusStock(StatusStock.OUT_OF_STOCK);
        ProductRequest productRequest = new ProductRequest("Product Update Test", "Test Update",20,category.getId(),StatusStock.IN_STOCK);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.saveAndFlush(product)).thenReturn(product);

        Product response = productService.updateProduct(product.getId(), productRequest);

        Assertions.assertEquals(product,response);
    }

    @Test
    @DisplayName("Verify if the product exists, if not throws an exception")
    void updateProductException() {
        ProductRequest productRequest = new ProductRequest("Product Update Test", "Test Update",20,null,StatusStock.IN_STOCK);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.updateProduct(anyInt(), productRequest));
    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void updateProductException2() {
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        ProductRequest productRequest = new ProductRequest("Product Update Test", "Test Update",20,1,StatusStock.IN_STOCK);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(categoryRepository.findById(productRequest.categoryID())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.updateProduct(product.getId(), productRequest));
    }

    @Test
    @DisplayName("Verify if the existing category is inactive, if it is, it throws an exception")
    void updateProductException3() {
        Category category = new Category("Test");
        category.setId(1);
        category.setStatusCategory(StatusCategory.INACTIVE);
        Product product = new Product("Product Test", "Test", 10, category);
        ProductRequest productRequest = new ProductRequest("Product Update Test", "Test Update",20,category.getId(),StatusStock.IN_STOCK);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Assertions.assertThrows(ResponseStatusException.class,()-> productService.updateProduct(product.getId(), productRequest));
    }

    @Test
    @DisplayName("Should remove the product from stock")
    void deleteProduct() {
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.saveAndFlush(product)).thenReturn(product);
        Product response = productService.deleteProduct(product.getId());

        Assertions.assertEquals(StatusStock.OUT_OF_STOCK,response.getStatusStock());
    }

    @Test
    @DisplayName("Verify is product exists, and throws a exception")
    void deleteProductException() {
        Assertions.assertThrows(ResponseStatusException.class, ()-> productService.deleteProduct(any()));
    }
}