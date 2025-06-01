package com.anycomp.anycomp_marketplace.dto;
import com.anycomp.anycomp_marketplace.model.Buyer;
import com.anycomp.anycomp_marketplace.repository.BuyerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Optional;

@Data
public class BuyerDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;    


    public Buyer convertToEntity(BuyerRepository buyerRepository) throws EntityNotFoundException{

        Buyer buyer = null;
        if(this.id != null){
            Optional<Buyer> optionalBuyer = buyerRepository.findById(this.id);
            if(optionalBuyer.isPresent())
                buyer = optionalBuyer.get();
            else
                throw new EntityNotFoundException("Buyer doesn't exist");
        }
        else{
            buyer = new Buyer();
            buyer.setId(this.id);
        }
        buyer.setName(this.name);
        buyer.setEmail(this.email);
        return buyer;
    }
}
