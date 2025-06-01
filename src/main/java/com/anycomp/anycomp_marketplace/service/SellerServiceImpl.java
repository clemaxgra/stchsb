package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.dto.SellerDTO;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.repository.SellerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService{
    private final SellerRepository sellerRepository;

    public List<Seller> getAllSellers(){
        return sellerRepository.findAll();
    }

    public Seller getSellerByID(Long id){
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if(optionalSeller.isPresent())
            return optionalSeller.get();
        log.info("Seller with id: {} doesn't exist", id);
        return null;
    }
    
    public Seller saveSeller(SellerDTO sellerDTO){

        Seller seller = convertToEntity(sellerDTO);
        if((seller.getId() != null) && sellerRepository.findById(seller.getId()).isPresent()){
            log.info("Seller with id: {} already exists", seller.getId());
            return null;
        }
        else{
            Seller savedSeller = sellerRepository.save(seller);
            log.info("Seller with id: {} saved successfully", savedSeller.getId());
            return savedSeller;
        }
    }

    public Seller updateSeller(SellerDTO sellerDTO){

        Seller existingSeller = convertToEntity(sellerDTO);

        Seller updatedSeller = sellerRepository.save(existingSeller);

        log.info("Seller with id: {} updated successfully", sellerDTO.getId());
        return updatedSeller;

    }
    
    public boolean deleteSellerByID(Long id){
        if(!sellerRepository.findById(id).isPresent()){
            log.info("Seller with id: {} doesn't exist", id);
            return false;
        }
        sellerRepository.deleteById(id);
        log.info("Seller with id: {} deleted successfully", id);
        return true;
    }

    public Seller convertToEntity(SellerDTO sellerDTO)  throws EntityNotFoundException{
        Seller seller = null;
        if(sellerDTO.getId() != null){
            Optional<Seller> optionalSeller = sellerRepository.findById(sellerDTO.getId());
            if(optionalSeller.isPresent())
                seller = optionalSeller.get();
            else
                throw new EntityNotFoundException("Seller doesn't exist");
        }
        else{
            seller = new Seller();
            seller.setId(sellerDTO.getId());
        }

        seller.setName(sellerDTO.getName());
        seller.setEmail(sellerDTO.getEmail());
        return seller;
    }   

}
