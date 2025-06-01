package com.anycomp.anycomp_marketplace.dto;

import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.model.Item;
import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.repository.ItemRepository;
import com.anycomp.anycomp_marketplace.repository.SellerRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.Optional;

@Data
public class ItemDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @Positive(message = "Price must be greater than 0")
    @NotNull(message = "Price is required")
    private Double price;

    @Min(value = 1, message = "Quantity must be at least 1")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    //@NotNull(message = "Seller ID is required") //not an argument in the body (?)
    private Long sellerId;

}
