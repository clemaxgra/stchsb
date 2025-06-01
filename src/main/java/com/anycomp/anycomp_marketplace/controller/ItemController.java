package com.anycomp.anycomp_marketplace.controller;

import com.anycomp.anycomp_marketplace.dto.ItemDTO;
import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.service.ItemService;
import com.anycomp.anycomp_marketplace.service.SellerService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;


    @GetMapping("/items")
    public ResponseEntity<Page<Item>> getItemsList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        
        return ResponseEntity.ok(itemService.getAllItems(pageable));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        Item foundItem = itemService.getItemByID(id);
        if(foundItem == null)
            return new ResponseEntity<>("Item with id " + id.toString() + " doesn't exist", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(foundItem, HttpStatus.FOUND);
    }

    @GetMapping("/sellers/{sellerId}/items")
    public ResponseEntity<Page<Item>> getItemsbySellerID(@PathVariable Long sellerId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
                
        Page<Item> items = itemService.getItemsBySellerID(sellerId, pageable);
        
        return ResponseEntity.ok(items);
    }

    @PostMapping("/sellers/{sellerId}/items")
    //public ResponseEntity<?> saveItem(@PathVariable Long sellerId, @RequestBody @Validated Item item, BindingResult bindingResult) {
    public ResponseEntity<?> saveItem(@PathVariable Long sellerId, @RequestBody @Valid ItemDTO itemDTO, BindingResult bindingResult) {
        itemDTO.setSellerId(sellerId);

        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }
        
        try {
            Item savedItem = itemService.saveItem(itemDTO);
            return new ResponseEntity<>(savedItem,(savedItem == null)?HttpStatus.CONFLICT:HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

    }   

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody @Valid ItemDTO itemDTO, BindingResult bindingResult) {
        itemDTO.setId(id);

        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }

        try{
            Item updatedItem = itemService.updateItem(itemDTO); //in existing implementation it should not return null

            return new ResponseEntity<>(updatedItem,(updatedItem == null)?HttpStatus.CONFLICT:HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
    
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItemByID(@PathVariable Long id) {
        boolean successfulDeletion = itemService.deleteItemByID((id));
        if(successfulDeletion)
            return ResponseEntity.ok().body("Deleted item succesfully!");
        else
            return ResponseEntity.ok().body("ID " + id.toString() + " doesn't exist");
    }  
    
}
