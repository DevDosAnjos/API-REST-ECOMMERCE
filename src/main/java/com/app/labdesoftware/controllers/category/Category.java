package com.app.labdesoftware.controllers.category;

import com.app.labdesoftware.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@Tag(name = "Category")
@SecurityRequirement(name = "Bearer Token")
public interface Category {

    @Operation(summary = "Get a Category by Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "404",description = "Category not found, Id is incorrect"),
            @ApiResponse(responseCode = "404",description = "Category inactive, enter another id")

    })
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponse> get(@AuthenticationPrincipal User user, @PathVariable("id") Integer id);

    @Operation(summary = "Get a List of All Categories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/all")
    ResponseEntity<List<CategoryResponse>> listAll(@AuthenticationPrincipal User user);

    @Operation(summary = "Get a List of Categories Actives", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/active")
    ResponseEntity<List<CategoryResponse>> listActive();

    @Operation(summary = "Get a List of Categories Inactives", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/inactive")
    ResponseEntity<List<CategoryResponse>> listInactive();

    @Operation(summary = "Create a new Category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Category created successfully"),
    })
    @PostMapping()
    ResponseEntity<com.app.labdesoftware.entities.Category> create(@RequestBody CategoryRequest categoryRequest);

    @Operation(summary = "Update a Category by Id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Category updated successfully"),
            @ApiResponse(responseCode = "404",description = "Category not found, Id is incorrect")
    })
    @PutMapping("/{id}")
    ResponseEntity<com.app.labdesoftware.entities.Category> update(@PathVariable("id") Integer id, @RequestBody CategoryRequest categoryRequest);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Category successfully disabled"),
            @ApiResponse(responseCode = "404",description = "Category not found, Id is incorrect")
    })
    @Operation(summary = "Delete a Category by Id", method = "DELETE")
    @DeleteMapping("/{id}")
    ResponseEntity<com.app.labdesoftware.entities.Category> delete(@PathVariable("id") Integer id);

}
