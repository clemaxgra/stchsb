package com.anycomp.anycomp_marketplace.service;

public interface PurchaseService {
    public void purchase(Long buyerID, Long itemID, int quantity) throws Exception;
}
