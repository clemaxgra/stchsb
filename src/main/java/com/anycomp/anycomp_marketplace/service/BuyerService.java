package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.dto.BuyerDTO;
import com.anycomp.anycomp_marketplace.model.Buyer;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface BuyerService {
    public Page<Buyer> getAllBuyers(Pageable pageable);
    public Buyer getBuyerByID(Long id);
    public Buyer saveBuyer(BuyerDTO buyerDTO);
    public Buyer updateBuyer(BuyerDTO buyerDTO);
    public boolean deleteBuyerByID(Long id);
    public Buyer convertToEntity(BuyerDTO buyerDTO) throws EntityNotFoundException;
}
