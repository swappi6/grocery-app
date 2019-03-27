package org.grocery.item;

import java.io.InputStream;
import java.util.List;

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
    
    public void delete(Long id){
        itemDao.delete(new Item(id));
    }
    
    public void createItem(ItemData itemData) throws GroceryException{
        Item item = new Item();
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setDiscountedPrice(itemData.getDiscountedPrice());
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
    
    public void updateItem(ItemData itemData, Long itemId) throws GroceryException{
        Item item = new Item();
        item.setId(itemId);
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setDiscountedPrice(itemData.getDiscountedPrice());
        InputStream inputStream =encodedStringHelper. getInputStream(itemData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(itemData.getName(), Constants.Buckets.ITEM, inputStream);
            item.setImageUrl(imageUrl);
        }
        Category cat = new Category();
        cat.setId(itemData.getParent());
        item.setCategory(cat);
        itemDao.update(item);
    }
}
