package com.anycomp.anycomp_marketplace.controller;

import com.anycomp.anycomp_marketplace.model.Purchase;
import com.anycomp.anycomp_marketplace.service.ItemService;
import com.anycomp.anycomp_marketplace.service.PurchaseService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;


    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(@RequestBody Map<String, Object> payload) {
        Long buyerId = Long.valueOf(payload.get("buyerId").toString());
        Long itemId = Long.valueOf(payload.get("itemId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());

        try{
            purchaseService.purchase(buyerId, itemId, quantity);
            return ResponseEntity.ok("Success");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

    }
    
}
