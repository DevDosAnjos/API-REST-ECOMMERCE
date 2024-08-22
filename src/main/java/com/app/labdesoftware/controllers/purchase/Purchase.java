package com.app.labdesoftware.controllers.purchase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/purchase")
@Tag(name = "Purchase")
@SecurityRequirement(name = "Bearer Token")
public interface Purchase {

    @Operation(summary = "Get a Purchase by Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "404",description = "Purchase id not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<PurchaseResponse> get(@PathVariable("id") Integer id);

    @Operation(summary = "Get a List of All Purchases", method = "GET")
    @GetMapping("/all")
    ResponseEntity<List<PurchaseResponse>> listAll();

    @Operation(summary = "Create a new Purchase", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Purchase made successfully"),
            @ApiResponse(responseCode = "403",description = "User not allowed"),
            @ApiResponse(responseCode = "404",description = "Order id not found")
    })
    @PostMapping()
    ResponseEntity<PurchaseResponse> create(@RequestBody PurchaseRequest purchaseRequest);
}
