package com.app.ecommerce.controllers.product;

import com.app.ecommerce.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@Tag(name = "Product")
@SecurityRequirement(name = "Bearer Token")
public interface Product {

    @Operation(summary = "Get a Product by Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "403",description = "Product out of stock, enter another id"),
            @ApiResponse(responseCode = "404",description = "Product id not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<com.app.ecommerce.entities.Product> get(@AuthenticationPrincipal User user, @PathVariable("id") Integer id);

    @Operation(summary = "Get a List of All Products", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/all")
    ResponseEntity<List<ProductResponse>> listAll(@AuthenticationPrincipal User user);

    @Operation(summary = "Get a List of Product by CategoryId", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "404",description = "Category id not found")
    })
    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<ProductResponse>> listByCategory(@AuthenticationPrincipal User user,@PathVariable("categoryId")Integer categoryId);

    @Operation(summary = "Get a List of Products by Price", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/price/{price}")
    ResponseEntity<List<ProductResponse>> listByPrice(@AuthenticationPrincipal User user,@PathVariable("price")Integer price);

    @Operation(summary = "Get a List of Products by Brand", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/brand/{brand}")
    ResponseEntity<List<ProductResponse>> listByBrand(@AuthenticationPrincipal User user,@PathVariable("brand")String brand);

    @Operation(summary = "Get a List of Products In Stock", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/inStock")
    ResponseEntity<List<ProductResponse>> listInStock();

    @Operation(summary = "Get a List of Products Out Of Stock", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/outOfStock")
    ResponseEntity<List<ProductResponse>> listOutOfStock();

    @Operation(summary = "Create a new Product", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Product created successfully"),
            @ApiResponse(responseCode = "400",description = "Category is inactive"),
            @ApiResponse(responseCode = "404",description = "Category id not found")
    })
    @PostMapping()
    ResponseEntity<com.app.ecommerce.entities.Product> create(@RequestBody ProductRequest productRequest);

    @Operation(summary = "Update a Product by Id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Product updated successful"),
            @ApiResponse(responseCode = "400",description = "Category is inactive"),
            @ApiResponse(responseCode = "404",description = "Product id not found, or , Category id not found"),
    })
    @PutMapping("/{id}")
    ResponseEntity<com.app.ecommerce.entities.Product> update(@PathVariable("id") Integer id, @RequestBody ProductRequest productRequest);

    @Operation(summary = "Delete a Product by Id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Product changed to out of stock")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<com.app.ecommerce.entities.Product> delete(@PathVariable("id") Integer id);
}
