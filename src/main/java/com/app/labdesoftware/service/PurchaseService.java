package com.app.labdesoftware.service;

import com.app.labdesoftware.controller.purchase.PurchaseRequest;
import com.app.labdesoftware.controller.purchase.PurchaseResponse;
import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumeration.StatusPurchase;
import com.app.labdesoftware.repository.CartRepository;
import com.app.labdesoftware.repository.PurchaseRepository;
import com.app.labdesoftware.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository;


    public PurchaseResponse findById(int id) throws BadRequestException {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (!purchase.isPresent()) {
            throw new BadRequestException("PURCHASE IS NOT FOUND");
        }
        return new PurchaseResponse(
                purchase.get().getId(),
                purchase.get().getClientId(),
                purchase.get().getCart().getId(),
                purchase.get().getTotal(),
                purchase.get().getPaymentMethod(),
                purchase.get().getDeliveryAddress(),
                purchase.get().getStatusPurchase()
        );
    }

    public List<PurchaseResponse> findAll() {
    List<Purchase> purchases = purchaseRepository.findAll();
    return PurchaseResponse.fromPurchase(purchases);
    }

    public List<PurchaseResponse> findAllByClientId(int id){
        List<Purchase> purchases = purchaseRepository.findAllByClientId(id);
        return PurchaseResponse.fromPurchase(purchases);
    }

    public List<PurchaseResponse> findByPending(StatusPurchase statusPurchase) {
        List<Purchase> purchases = purchaseRepository.findByStatusPurchase(StatusPurchase.PENDING);
        return PurchaseResponse.fromPurchase(purchases);
    }

    public List<PurchaseResponse> findByCanceled(StatusPurchase statusPurchase) {
        List<Purchase> purchases = purchaseRepository.findByStatusPurchase(StatusPurchase.CANCELED);
        return PurchaseResponse.fromPurchase(purchases);
    }

    public Purchase create(PurchaseRequest purchaseRequest, CartRepository cartRepository) {
        //TODO:ADICIONAR A AUTENTICAÇÃO DO USUARIO PARA PODER SALVAR/CRIAR UMA NOVA COMPRA
        User user = userRepository.findById(purchaseRequest.getUserId()).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
        Purchase purchase = Purchase.from(purchaseRequest, cartRepository);
        return purchaseRepository.save(purchase);
    }

    public void update(int id, PurchaseRequest purchaseRequest) throws BadRequestException {
        //TODO:ADICIONAR A AUTENTICAÇÃO DO USUARIO PARA PODER ATUALIZAR UMA COMPRA
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if(!purchase.isPresent()){
            throw new BadRequestException("PURCHASE IS NOT FOUND");
        }
        if (purchaseRequest.paymentMethod() != null) {
            purchase.get().setPaymentMethod(purchaseRequest.paymentMethod());
        }
        purchaseRepository.saveAndFlush(purchase.get());
    }

    public void delete(int id) {
        //TODO:ADICIONAR A AUTENTICAÇÃO DO USUARIO PARA PODER DELETAR UMA COMPRA
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if(purchase.isPresent()){
            purchase.get().setStatusPurchase(StatusPurchase.CANCELED);
            purchaseRepository.saveAndFlush(purchase.get());
        }
    }
}
