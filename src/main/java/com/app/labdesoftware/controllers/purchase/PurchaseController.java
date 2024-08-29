package com.app.labdesoftware.controllers.purchase;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PurchaseController implements Purchase{

    @Autowired
    private PurchaseService purchaseService;

    public ResponseEntity<PurchaseResponse> get(User user,Integer id) {
        PurchaseResponse purchase = purchaseService.getPurchase(id,user);
        return ResponseEntity.ok(purchase);
    }

    public ResponseEntity<List<PurchaseResponse>> listAll(User user) {
        List<PurchaseResponse> purchases = purchaseService.listAllPurchases(user);
        return ResponseEntity.ok(purchases);
    }

    public ResponseEntity<PurchaseResponse> create(User user,PurchaseRequest purchaseRequest){
        return ResponseEntity.ok(purchaseService.createPurchase(user,purchaseRequest));
    }
}
