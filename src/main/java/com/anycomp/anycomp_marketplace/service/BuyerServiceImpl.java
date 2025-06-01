package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerServiceImpl implements BuyerService{
    private final BuyerRepository buyerRepository;

    public List<Buyer> getAllBuyers(){
        return buyerRepository.findAll();
    }

    public Buyer getBuyerByID(Long id){
        Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
        if(optionalBuyer.isPresent())
            return optionalBuyer.get();
        log.info("Buyer with id: {} doesn't exist", id);
        return null;
    }
    
    public Buyer saveBuyer(Buyer buyer){

        if((buyer.getId() != null) && buyerRepository.findById(buyer.getId()).isPresent()){
            log.info("Buyer with id: {} already exists", buyer.getId());
            return null;
        }
        else{
            Buyer savedBuyer = buyerRepository.save(buyer);
            log.info("Buyer with id: {} saved successfully", savedBuyer.getId());
            return savedBuyer;
        }
    }

    public Buyer updateBuyer(Buyer buyer){

        Long targetID = buyer.getId();
        Optional<Buyer> optionalBuyer = buyerRepository.findById(targetID);
    
        if (optionalBuyer.isEmpty()) {
            throw new EntityNotFoundException("Buyer with ID " + targetID + " not found");
        }

        Buyer existingBuyer = optionalBuyer.get();

        existingBuyer.setName(buyer.getName());
        existingBuyer.setEmail(buyer.getEmail());

        Buyer updatedBuyer = buyerRepository.save(existingBuyer);

        log.info("Buyer with id: {} updated successfully", targetID);
        return updatedBuyer;

    }
    
    public boolean deleteBuyerByID(Long id){
        if(!buyerRepository.findById(id).isPresent()){
            log.info("Buyer with id: {} doesn't exist", id);
            return false;
        }
        buyerRepository.deleteById(id);
        log.info("Buyer with id: {} deleted successfully", id);
        return true;
    }
}
