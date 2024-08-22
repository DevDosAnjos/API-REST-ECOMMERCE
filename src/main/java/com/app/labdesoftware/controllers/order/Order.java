package com.app.labdesoftware.controllers.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@Tag(name = "Order")
@SecurityRequirement(name = "Bearer Token")
public interface Order {

    @Operation(summary = "Get a Order by Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "403",description = "User not allowed"),
            @ApiResponse(responseCode = "404",description = "Order id not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> get(@PathVariable("id") Integer id);

    @Operation(summary = "Get a List of All Orders", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
    })
    @GetMapping("/all")
    ResponseEntity<List<OrderResponse>> listAll();

    @Operation(summary = "Create a new Order", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Order made successfully"),
            @ApiResponse(responseCode = "400",description = "Product Id is duplicated, or , Product out of stock "),
            @ApiResponse(responseCode = "404",description = "Product Id not found")
    })
    @PostMapping()
    ResponseEntity<List<OrderResponse>> create(@RequestBody OrderRequest orderRequest);
}
