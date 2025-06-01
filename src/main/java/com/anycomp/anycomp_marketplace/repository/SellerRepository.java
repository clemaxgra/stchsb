package com.anycomp.anycomp_marketplace.repository;
import com.anycomp.anycomp_marketplace.model.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long>{
    
}

