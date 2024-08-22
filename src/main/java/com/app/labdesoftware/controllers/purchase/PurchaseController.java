package com.app.labdesoftware.controllers.purchase;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PurchaseController implements Purchase{

    @Autowired
    private PurchaseService purchaseService;

    public ResponseEntity<PurchaseResponse> get(Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PurchaseResponse purchase = purchaseService.getPurchase(id,user);
        return ResponseEntity.ok(purchase);
    }

    public ResponseEntity<List<PurchaseResponse>> listAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PurchaseResponse> purchases = purchaseService.listAllPurchases(user);
        return ResponseEntity.ok(purchases);
    }

    public ResponseEntity<PurchaseResponse> create(PurchaseRequest purchaseRequest){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(purchaseService.createPurchase(user,purchaseRequest));
    }
}
