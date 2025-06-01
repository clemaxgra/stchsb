package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.repository.ItemRepository;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;
import com.anycomp.anycomp_marketplace.repository.PurchaseRepository;


import com.anycomp.anycomp_marketplace.model.Item;
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
    public void purchase(Long buyerID, Long itemID, int quantity) throws Exception{
        if(quantity <= 0)
            throw new Exception("Invalid purchase amount");
        
        Optional<Buyer> optionalBuyer = buyerRepository.findById(buyerID);
        Optional<Item> optionalItem = itemRepository.findById(buyerID);

        if(!optionalBuyer.isPresent())
            throw new Exception("Buyer doesn't exist");

        if(!optionalItem.isPresent())
            throw new Exception("Item doesn't exist");

        Buyer buyer = optionalBuyer.get();
        Item item = optionalItem.get();

        item.setQuantity(item.getQuantity() - quantity);

        if(item.getQuantity() < 0)
            throw new Exception("Not enough item in stock");

        Purchase purchase = new Purchase();
        purchase.setBuyer(buyer);
        purchase.setItem(item);
        purchase.setQuantity(quantity);
        purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));

        purchaseRepository.save(purchase);
        itemRepository.save(item);

    }
}
