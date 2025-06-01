package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.dto.ItemDTO;
import com.anycomp.anycomp_marketplace.model.Item;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    public Page<Item> getAllItems(Pageable pageable);
    public Item getItemByID(Long id);
    public Page<Item> getItemsBySellerID(Long sellerID, Pageable pageable);
    public Item saveItem(ItemDTO itemDTO);
    public Item updateItem(ItemDTO itemDTO) throws Exception;
    public boolean deleteItemByID(Long id);
    public Item convertToEntity(ItemDTO itemDTO) throws EntityNotFoundException;
}
