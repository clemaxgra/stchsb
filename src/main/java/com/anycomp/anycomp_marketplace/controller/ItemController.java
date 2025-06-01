package com.anycomp.anycomp_marketplace.controller;

import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.service.ItemService;
import com.anycomp.anycomp_marketplace.service.SellerService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

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
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private SellerService sellerService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItemsList() {
        return ResponseEntity.ok(itemService.getAllItems());
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
    public ResponseEntity<List<Item>> getItemsbySellerID(@PathVariable Long sellerId) {
        List<Item> items = itemService.getItemsBySellerID(sellerId);
        return new ResponseEntity<>(items, HttpStatus.FOUND);
    }

    @PostMapping("/sellers/{sellerId}/items")
    //public ResponseEntity<?> saveItem(@PathVariable Long sellerId, @RequestBody @Validated Item item, BindingResult bindingResult) {
    public ResponseEntity<?> saveItem(@PathVariable Long sellerId, @RequestBody Item item) {
        //log.info("Seller id is {}", sellerId);
        Seller seller = sellerService.getSellerByID(sellerId);

        if (seller == null) {
            return ResponseEntity.badRequest().body("Invalid seller ID");
        }

        item.setSeller(seller);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Item> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }
        
        try {
            Item savedItem = itemService.saveItem(item, sellerId);
            return new ResponseEntity<>(savedItem,(savedItem == null)?HttpStatus.CONFLICT:HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

    }   

    @PutMapping("/items/{id}")
    public ResponseEntity<?> saveItem(@RequestBody Item item, @PathVariable Long id) {
        item.setId(id);

        try{
            Item updatedItem = itemService.updateItem(item); //in existing implementation it should not return null

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
