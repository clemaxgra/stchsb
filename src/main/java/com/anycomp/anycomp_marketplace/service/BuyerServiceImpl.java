package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.dto.BuyerDTO;
import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerServiceImpl implements BuyerService{
    private final BuyerRepository buyerRepository;

    public Page<Buyer> getAllBuyers(Pageable pageable){
        return buyerRepository.findAll(pageable);
    }

    public Buyer getBuyerByID(Long id){
        Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
        if(optionalBuyer.isPresent())
            return optionalBuyer.get();
        log.info("Buyer with id: {} doesn't exist", id);
        return null;
    }
    
    public Buyer saveBuyer(BuyerDTO buyerDTO){
        Buyer savedBuyer = buyerRepository.save(convertToEntity(buyerDTO));
        log.info("Buyer with id: {} saved successfully", savedBuyer.getId());
        return savedBuyer;
    }

    public Buyer updateBuyer(BuyerDTO buyerDTO){

        Buyer existingBuyer = convertToEntity(buyerDTO);
        Buyer updatedBuyer = buyerRepository.save(existingBuyer);

        log.info("Buyer with id: {} updated successfully", updatedBuyer.getId());
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

    public Buyer convertToEntity(BuyerDTO buyerDTO) throws EntityNotFoundException{

        Buyer buyer = null;
        if(buyerDTO.getId() != null){
            Optional<Buyer> optionalBuyer = buyerRepository.findById(buyerDTO.getId());
            if(optionalBuyer.isPresent())
                buyer = optionalBuyer.get();
            else
                throw new EntityNotFoundException("Buyer doesn't exist");
        }
        else{
            buyer = new Buyer();
            buyer.setId(buyerDTO.getId());
        }
        buyer.setName(buyerDTO.getName());
        buyer.setEmail(buyerDTO.getEmail());
        return buyer;
    }

}
