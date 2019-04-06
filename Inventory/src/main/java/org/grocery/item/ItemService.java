package org.grocery.item;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.EncodedStringHelper;
import org.grocery.Utils.FileStore;
import org.grocery.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemService {
    
    @Autowired
    ItemDao itemDao;
    @Autowired
    FileStore store;
    @Autowired
    EncodedStringHelper encodedStringHelper;
    
    public List<Item> getAllItems() throws Exception {
        List<Item> items = itemDao.findAll();
        return items;
    }
    
    public List<Item> findByCategory(Long parent) throws Exception {
        return itemDao.findByCategory(parent);
    }
    
    public void delete(Long id) throws GroceryException{
        Optional<Item> optionalItem = itemDao.findById(id);
        if (!optionalItem.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_CATEGORY_ID);
        Item item = optionalItem.get();
        store.delete(item.getName(), Constants.Buckets.ITEM); 
        itemDao.delete(item);
    }
    
    public void createItem(ItemData itemData) throws GroceryException{
        Item item = new Item();
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setDiscountedPrice(itemData.getDiscountedPrice());
        item.setSubscribable(itemData.getSubscribable());
        InputStream inputStream =encodedStringHelper. getInputStream(itemData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(itemData.getName(), Constants.Buckets.ITEM, inputStream);
            item.setImageUrl(imageUrl);
        }
        Category cat = new Category();
        cat.setId(itemData.getParent());
        item.setCategory(cat);
        itemDao.create(item);
    }
    
    public List<Item> searchItem(String search) throws GroceryException{
        List<Item> itemByName = itemDao.searchByName(search);
        List<Item> itemByDesc = itemDao.searchByDescription(search);
        itemByName.addAll(itemByDesc);
        return itemByName;
    }
    
    public void updateItem(ItemData itemData, Long itemId) throws GroceryException{
        Optional<Item> optionalItem = itemDao.findById(itemId);
        if (!optionalItem.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ITEM_ID);
        Item item = optionalItem.get();
        if (itemData.getName() != null) {
            String imageUrl = store.rename(item.getName(), Constants.Buckets.ITEM, itemData.getName());
            item.setName(itemData.getName());
            item.setImageUrl(imageUrl);
        }
        if (itemData.getDescription() != null)
            item.setDescription(itemData.getDescription());
        if (itemData.getPrice() != null)
            item.setPrice(itemData.getPrice());
        if (itemData.getDiscountedPrice() != null)
            item.setDiscountedPrice(itemData.getDiscountedPrice());
        if (itemData.getSubscribable() != null)
            item.setSubscribable(itemData.getSubscribable());
        InputStream inputStream =encodedStringHelper. getInputStream(itemData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(item.getName(), Constants.Buckets.ITEM, inputStream);
            item.setImageUrl(imageUrl);
        }
        if (itemData.getParent() != null) {
            Category cat = new Category();
            cat.setId(itemData.getParent());
            item.setCategory(cat);
        }
        itemDao.update(item);
    }
}
