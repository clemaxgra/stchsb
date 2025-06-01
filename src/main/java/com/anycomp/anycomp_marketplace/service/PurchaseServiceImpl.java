package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.repository.ItemRepository;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;
import com.anycomp.anycomp_marketplace.repository.PurchaseRepository;


import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.dto.PurchaseDTO;
import com.anycomp.anycomp_marketplace.exception.PurchaseValidationException;
import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.model.Purchase;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService{
    private final BuyerRepository buyerRepository;
    private final ItemRepository itemRepository;
    private final PurchaseRepository purchaseRepository;
    
    @Transactional
    public void purchase(PurchaseDTO purchaseDTO) throws PurchaseValidationException{
        Purchase purchase = convertToEntity(purchaseDTO);
        
        Integer quantity = purchaseDTO.getQuantity();

        if(quantity <= 0)
            throw new PurchaseValidationException("Invalid purchase amount");
        Item item = purchase.getItem();
        item.setQuantity(item.getQuantity() - quantity);

        if(item.getQuantity() < 0)
            throw new PurchaseValidationException("Not enough item in stock");

        purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));

        purchaseRepository.save(purchase);
        itemRepository.save(item);

        log.info("Purchase with id: {} saved successfully", purchase.getId());
    }


    public Purchase convertToEntity(PurchaseDTO purchaseDTO)  throws EntityNotFoundException{
        Purchase purchase = null;
        if(purchaseDTO.getId() != null){
            Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseDTO.getId());
            if(optionalPurchase.isPresent())
                purchase = optionalPurchase.get();
            else
                throw new EntityNotFoundException("Purchase doesn't exist");
        }
        else{
            purchase = new Purchase();
            purchase.setId(purchaseDTO.getId());
        }

        Optional<Buyer> optionalBuyer = buyerRepository.findById(purchaseDTO.getBuyerId());
        if(!optionalBuyer.isPresent())
            throw new EntityNotFoundException("Buyer does not exist");
        purchase.setBuyer(optionalBuyer.get());

        Optional<Item> optimalItem = itemRepository.findById(purchaseDTO.getItemId());
        if(!optimalItem.isPresent())
            throw new EntityNotFoundException("Item does not exist");
        purchase.setItem(optimalItem.get());

        purchase.setQuantity(purchaseDTO.getQuantity());
        return purchase;
    }   


}
