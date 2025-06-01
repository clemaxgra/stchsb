package com.anycomp.anycomp_marketplace.service;

import com.anycomp.anycomp_marketplace.dto.PurchaseDTO;
import com.anycomp.anycomp_marketplace.exception.PurchaseValidationException;
import com.anycomp.anycomp_marketplace.model.Purchase;

import jakarta.persistence.EntityNotFoundException;

public interface PurchaseService {
    public void purchase(PurchaseDTO purchaseDTO) throws PurchaseValidationException;
    public Purchase convertToEntity(PurchaseDTO purchaseDTO) throws EntityNotFoundException;
}
