package com.anycomp.anycomp_marketplace.dto;

import java.util.List;
import java.util.Optional;

import com.anycomp.anycomp_marketplace.model.Seller;
import com.anycomp.anycomp_marketplace.repository.SellerRepository;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerDTO {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public Seller convertToEntity(SellerRepository sellerRepository)  throws EntityNotFoundException{
        Seller seller = null;
        if(this.id != null){
            Optional<Seller> optionalSeller = sellerRepository.findById(this.id);
            if(optionalSeller.isPresent())
                seller = optionalSeller.get();
            else
                throw new EntityNotFoundException("Seller doesn't exist");
        }
        else{
            seller = new Seller();
            seller.setId(this.id);
        }

        seller.setName(this.name);
        seller.setEmail(this.email);
        return seller;
    }   




}
