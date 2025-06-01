package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.model.Buyer;

public interface BuyerService {
    public List<Buyer> getAllBuyers();
    public Buyer getBuyerByID(Long id);
    public Buyer saveBuyer(Buyer buyer);
    public Buyer updateBuyer(Buyer buyer);
    public boolean deleteBuyerByID(Long id);
}
