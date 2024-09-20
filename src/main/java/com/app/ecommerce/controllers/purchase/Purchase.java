package com.app.ecommerce.controllers.purchase;

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
    ResponseEntity<PurchaseResponse> get(@AuthenticationPrincipal User user,@PathVariable("id") Integer id);

    @Operation(summary = "Get a List of All Purchases", method = "GET")
    @GetMapping("/all")
    ResponseEntity<List<PurchaseResponse>> listAll(@AuthenticationPrincipal User user);

    @Operation(summary = "Create a new Purchase", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Purchase made successfully"),
            @ApiResponse(responseCode = "403",description = "User not allowed"),
            @ApiResponse(responseCode = "404",description = "Order id not found")
    })
    @PostMapping()
    ResponseEntity<PurchaseResponse> create(@AuthenticationPrincipal User user,@RequestBody PurchaseRequest purchaseRequest);
}
