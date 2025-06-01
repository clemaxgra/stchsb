package com.anycomp.anycomp_marketplace.service;

import java.util.List;

import com.anycomp.anycomp_marketplace.model.Item;

public interface ItemService {
    public List<Item> getAllItems();
    public Item getItemByID(Long id);
    public List<Item> getItemsBySellerID(Long sellerID);
    public Item saveItem(Item item, Long sellerID);
    public Item updateItem(Item seller) throws Exception;
    public boolean deleteItemByID(Long id);
}
