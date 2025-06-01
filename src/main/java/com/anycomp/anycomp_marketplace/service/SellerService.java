package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.model.Seller;

public interface SellerService {
    public List<Seller> getAllSellers();
    public Seller getSellerByID(Long id);
    public Seller saveSeller(Seller seller);
    public Seller updateSeller(Seller seller);
    public boolean deleteSellerByID(Long id);
}
