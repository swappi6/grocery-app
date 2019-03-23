package org.grocery.item;

import java.util.List;

import org.grocery.Error.GroceryException;
import org.grocery.Utils.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemService {
    
    @Autowired
    ItemDao itemDao;
    
    @Autowired
    FileStore store;
    
    public List<Item> getAllItems() throws Exception {
        return itemDao.findAll();
    }
    
    public List<Item> findByCategory(String parent) throws Exception {
        return itemDao.findByCategory(parent);
    }
    
    public void createCategory(ItemData itemData) throws GroceryException{
        Item item = new Item();
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setParent(itemData.getParent());
        item.setPrice(itemData.getPrice());
        item.setDiscountedPrice(itemData.getDiscountedPrice());
//        String imageUrl = store.upload(itemData.getImage(), itemData.getName(), Constants.Buckets.ITEM);
//        item.setImageUrl(imageUrl);
        itemDao.create(item);
    }
}
