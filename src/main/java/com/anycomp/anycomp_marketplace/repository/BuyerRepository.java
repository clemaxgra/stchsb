package com.anycomp.anycomp_marketplace.repository;
import com.anycomp.anycomp_marketplace.model.Buyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long>{
    
}
