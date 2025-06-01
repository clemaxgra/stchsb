package com.anycomp.anycomp_marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne()
    @JoinColumn(name = "buyer_id")
    @NotNull(message = "Buyer is required")
    //@JsonIgnore
    @JsonBackReference(value = "buyer-purchases")
    private Buyer buyer;

    @ManyToOne()
    @JoinColumn(name = "item_id")
    @NotNull(message = "Item is required")
    //@JsonIgnore
    @JsonBackReference(value = "item-purchases")
    private Item item;

    @Min(value = 1, message = "Quantity must be at least 1")
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    
    private Timestamp purchaseDate;

}
