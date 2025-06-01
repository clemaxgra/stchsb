package com.anycomp.anycomp_marketplace.controller;

import com.anycomp.anycomp_marketplace.dto.SellerDTO;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.service.SellerService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

@RestController
@Slf4j
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("")
    public ResponseEntity<List<Seller>> getSellersList() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeller(@PathVariable Long id) {
        Seller foundSeller = sellerService.getSellerByID(id);
        if(foundSeller == null)
            return new ResponseEntity<>("Seller with id " + id.toString() + " doesn't exist", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(foundSeller, HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> saveSeller(@RequestBody @Validated SellerDTO sellerDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }

        try {
            Seller savedSeller = sellerService.saveSeller(sellerDTO);
            return new ResponseEntity<>(savedSeller,(savedSeller == null)?HttpStatus.CONFLICT:HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

    }   

    @PutMapping("/{id}")
    public ResponseEntity<?> saveSeller(@RequestBody @Validated SellerDTO sellerDTO, BindingResult bindingResult, @PathVariable Long id) {
        sellerDTO.setId(id);
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }

        try{
            Seller updatedSeller = sellerService.updateSeller(sellerDTO); //in existing implementation it should not return null
            return new ResponseEntity<>(updatedSeller,(updatedSeller == null)?HttpStatus.CONFLICT:HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSellerByID(@PathVariable Long id) {
        boolean successfulDeletion = sellerService.deleteSellerByID((id));
        if(successfulDeletion)
            return ResponseEntity.ok().body("Deleted seller succesfully!");
        else
            return ResponseEntity.ok().body("ID " + id.toString() + " doesn't exist");
    }  

}
