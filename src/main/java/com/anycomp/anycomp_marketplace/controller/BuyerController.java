package com.anycomp.anycomp_marketplace.controller;

import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.dto.BuyerDTO;
import com.anycomp.anycomp_marketplace.service.BuyerService;

import jakarta.validation.Valid;
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
@RequestMapping("/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("")
    public ResponseEntity<List<Buyer>> getBuyersList() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBuyer(@PathVariable Long id) {
        Buyer foundBuyer = buyerService.getBuyerByID(id);
        if(foundBuyer == null)
            return new ResponseEntity<>("Buyer with id " + id.toString() + " doesn't exist", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(foundBuyer, HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> saveBuyer(@RequestBody @Valid BuyerDTO buyerDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }

        try {
            Buyer savedBuyer = buyerService.saveBuyer(buyerDTO);
            return new ResponseEntity<>(savedBuyer,(savedBuyer == null)?HttpStatus.CONFLICT:HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

    }   

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBuyer(@RequestBody @Valid BuyerDTO buyerDTO, BindingResult bindingResult, @PathVariable Long id) {
        buyerDTO.setId(id);
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());            
        }

        try{
            Buyer updatedBuyer = buyerService.updateBuyer(buyerDTO); //in existing implementation it should not return null
            return new ResponseEntity<>(updatedBuyer,(updatedBuyer == null)?HttpStatus.CONFLICT:HttpStatus.OK);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuyerByID(@PathVariable Long id) {
        boolean successfulDeletion = buyerService.deleteBuyerByID((id));
        if(successfulDeletion)
            return ResponseEntity.ok().body("Deleted buyer succesfully!");
        else
            return ResponseEntity.ok().body("ID " + id.toString() + " doesn't exist");
    }  

}
