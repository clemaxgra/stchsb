package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.dto.ItemDTO;
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

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;

    public Page<Item> getAllItems(Pageable pageable){
        return itemRepository.findAll(pageable);
    }

    public Item getItemByID(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent())
            return optionalItem.get();
        log.info("Item with id: {} doesn't exist", id);
        return null;
    }

    public Page<Item> getItemsBySellerID(Long sellerID, Pageable pageable){
        return itemRepository.findBySellerId(sellerID, pageable);
    }

    public Item saveItem(ItemDTO itemDTO){

        Item item = convertToEntity(itemDTO);

        Item savedItem = itemRepository.save(item);
        log.info("Item with id: {} saved successfully", savedItem.getId());
        return savedItem;

    }

    public Item updateItem(ItemDTO itemDTO) throws Exception{

        Item item = convertToEntity(itemDTO);
        Item updatedItem = itemRepository.save(item);

        log.info("Item with id: {} updated successfully", updatedItem.getId());
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

    public Item convertToEntity(ItemDTO itemDTO) throws EntityNotFoundException{

        Item item = null;
        if(itemDTO.getId() != null){
            Optional<Item> optionalItem = itemRepository.findById(itemDTO.getId());
            if(optionalItem.isPresent())
                item = optionalItem.get();
            else
                throw new EntityNotFoundException("Item doesn't exist");
        }
        else{
            item = new Item();
            item.setId(itemDTO.getId());
        }
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());

        Optional<Seller> optionalSeller = sellerRepository.findById(itemDTO.getSellerId());
        
        if(!optionalSeller.isPresent())
            throw new EntityNotFoundException("Seller does not exist");

        item.setSeller(optionalSeller.get());
        return item;
    }
}
