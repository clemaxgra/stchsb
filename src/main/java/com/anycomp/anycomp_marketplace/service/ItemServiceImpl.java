package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.repository.ItemRepository;
import com.anycomp.anycomp_marketplace.repository.SellerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item getItemByID(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent())
            return optionalItem.get();
        log.info("Item with id: {} doesn't exist", id);
        return null;
    }

    public List<Item> getItemsBySellerID(Long sellerID){
        return itemRepository.findBySellerId(sellerID);
    }

    public Item saveItem(Item item, Long sellerID){

        Optional<Seller> optionalSeller = sellerRepository.findById(sellerID);
    
        if (optionalSeller.isEmpty()) {
            throw new EntityNotFoundException("Seller with ID " + sellerID + " not found");
        }

        if((item.getId() != null) && itemRepository.findById(item.getId()).isPresent()){
            log.info("Item with id: {} already exists", item.getId());
            return null;
        }
        else{
            Item savedItem = itemRepository.save(item);
            log.info("Item with id: {} saved successfully", savedItem.getId());
            return savedItem;
        }
    }

    public Item updateItem(Item item) throws Exception{

        Long targetID = item.getId();
        Optional<Item> optionalItem = itemRepository.findById(targetID);
    
        if (optionalItem.isEmpty()) {
            throw new EntityNotFoundException("Item with ID " + targetID + " not found");
        }

        Item existingItem = optionalItem.get();


        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setPrice(item.getPrice());
        existingItem.setQuantity(item.getQuantity());
        //seller will not be changed

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Item>> violations = validator.validate(existingItem);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Item> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            log.info("Item failed to update. {}", errors.toString());
            throw new Exception(errors.toString());

        }


        Item updatedItem = itemRepository.save(existingItem);

        log.info("Item with id: {} updated successfully", targetID);
        return updatedItem;

    }
    
    public boolean deleteItemByID(Long id){
        if(!itemRepository.findById(id).isPresent()){
            log.info("Item with id: {} doesn't exist", id);
            return false;
        }
        itemRepository.deleteById(id);
        log.info("Item with id: {} deleted successfully", id);
        return true;
    }
}
