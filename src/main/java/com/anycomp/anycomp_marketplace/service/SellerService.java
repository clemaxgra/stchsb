package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.dto.SellerDTO;
import com.anycomp.anycomp_marketplace.model.Seller;
import jakarta.persistence.EntityNotFoundException;

public interface SellerService {
    public List<Seller> getAllSellers();
    public Seller getSellerByID(Long id);
    public Seller saveSeller(SellerDTO sellerDTO);
    public Seller updateSeller(SellerDTO sellerDTO);
    public boolean deleteSellerByID(Long id);
    public Seller convertToEntity(SellerDTO sellerDTO) throws EntityNotFoundException;

}
