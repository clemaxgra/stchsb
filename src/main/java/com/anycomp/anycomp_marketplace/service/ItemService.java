package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.dto.ItemDTO;
import com.anycomp.anycomp_marketplace.model.Item;

import jakarta.persistence.EntityNotFoundException;

public interface ItemService {
    public List<Item> getAllItems();
    public Item getItemByID(Long id);
    public List<Item> getItemsBySellerID(Long sellerID);
    public Item saveItem(ItemDTO itemDTO);
    public Item updateItem(ItemDTO itemDTO) throws Exception;
    public boolean deleteItemByID(Long id);
    public Item convertToEntity(ItemDTO itemDTO) throws EntityNotFoundException;
}
