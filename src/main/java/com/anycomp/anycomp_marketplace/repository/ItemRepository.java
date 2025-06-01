package com.anycomp.anycomp_marketplace.repository;
import com.anycomp.anycomp_marketplace.model.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
    List<Item> findBySellerId(Long seller_id);
}

