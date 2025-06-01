package com.anycomp.anycomp_marketplace.dto;

import java.sql.Timestamp;
import java.util.Optional;

import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.model.Purchase;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;
import com.anycomp.anycomp_marketplace.repository.ItemRepository;
import com.anycomp.anycomp_marketplace.repository.PurchaseRepository;
import com.anycomp.anycomp_marketplace.repository.SellerRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseDTO {
    private Long id;
    
    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @NotNull(message = "Quantity is required")
    private Integer quantity;


}
