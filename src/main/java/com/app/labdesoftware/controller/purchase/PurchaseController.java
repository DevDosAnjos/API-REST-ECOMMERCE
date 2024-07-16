package com.app.labdesoftware.controller.purchase;

import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.enumeration.StatusPurchase;
import com.app.labdesoftware.repository.CartRepository;
import com.app.labdesoftware.service.PurchaseService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchase(@PathVariable("id") int id) throws BadRequestException {
        PurchaseResponse purchase = purchaseService.findById(id);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<PurchaseResponse>> listAllByClient(@PathVariable("id") int id){
        List<PurchaseResponse> purchases = purchaseService.findAllByClientId(id);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseResponse>> listAll() {
        List<PurchaseResponse> purchases = purchaseService.findAll();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PurchaseResponse>> listPurchasesPending(){
        List<PurchaseResponse> purchases = purchaseService.findByPending(StatusPurchase.PENDING);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/canceled")
    public ResponseEntity<List<PurchaseResponse>>listPurchasesCanceled(){
        List<PurchaseResponse> purchases = purchaseService.findByCanceled(StatusPurchase.CANCELED);
        return ResponseEntity.ok(purchases);
    }

    @PostMapping()
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseRequest purchaseRequest){
        Purchase purchase = purchaseService.create(purchaseRequest, cartRepository);
        return ResponseEntity.ok(purchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePurchase(@PathVariable("id") int id, @RequestBody PurchaseRequest purchaseRequest) throws BadRequestException {
        purchaseService.update(id,purchaseRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePurchase(@PathVariable("id") int id){
        purchaseService.delete(id);
        return ResponseEntity.ok().build();
    }
}
