package com.anycomp.anycomp_marketplace.repository;
import com.anycomp.anycomp_marketplace.model.Item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
    Page<Item> findBySellerId(Long seller_id, Pageable pageable);
}

