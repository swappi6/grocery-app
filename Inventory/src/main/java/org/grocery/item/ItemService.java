package org.grocery.item;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        store.delete(Constants.Buckets.ITEM, item.getImageUrl()); 
        itemDao.delete(item);
    }
    
    public void createItem(ItemData itemData) throws GroceryException{
        Item item = new Item();
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setDiscountedPrice(itemData.getDiscountedPrice());
        item.setSubscribable(itemData.getSubscribable());
        item.setLeastCount(itemData.getLeastCount());
        InputStream inputStream =encodedStringHelper. getInputStream(itemData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(Constants.Buckets.ITEM, inputStream);
            item.setImageUrl(imageUrl);
        }
        Category cat = new Category();
        cat.setId(itemData.getParent());
        item.setCategory(cat);
        itemDao.create(item);
    }
    
    public Set<Item> searchItem(String search) throws GroceryException{
        List<Item> itemByName = itemDao.searchByName(search);
        List<Item> itemByDesc = itemDao.searchByDescription(search);
        Set<Item> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(itemByName);
        linkedHashSet.addAll(itemByDesc);
        return linkedHashSet;
    }
    
    public void updateItem(ItemData itemData, Long itemId) throws GroceryException{
        Optional<Item> optionalItem = itemDao.findById(itemId);
        if (!optionalItem.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ITEM_ID);
        Item item = optionalItem.get();
        if (itemData.getName() != null) {
            item.setName(itemData.getName());
        }
        if (itemData.getDescription() != null)
            item.setDescription(itemData.getDescription());
        if (itemData.getPrice() != null)
            item.setPrice(itemData.getPrice());
        if (itemData.getDiscountedPrice() != null)
            item.setDiscountedPrice(itemData.getDiscountedPrice());
        if (itemData.getSubscribable() != null)
            item.setSubscribable(itemData.getSubscribable());
        if (itemData.getLeastCount() != null)
            item.setLeastCount(itemData.getLeastCount());
        InputStream inputStream =encodedStringHelper. getInputStream(itemData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(Constants.Buckets.ITEM, inputStream);
            item.setImageUrl(imageUrl);
        }
        if (itemData.getParent() != null) {
            Category cat = new Category();
            cat.setId(itemData.getParent());
            item.setCategory(cat);
        }
        itemDao.update(item);
    }
    
    public Double getCartPrice(List<ItemQuantity> cart) throws GroceryException{
        List<Long> itemIds = cart.stream().map(e -> e.getItemId()).collect(Collectors.toList());
        System.out.println("**********"+itemIds+"************");
        List<Item> items = itemDao.findInIds(itemIds);
        Double sum = 0D;
        for (Long id : itemIds) {
            sum+= getQuantity(cart, id) * getPrice(items, id);
        }
        return sum;
    }
    
    public List<Item> getItems(List<ItemQuantity> cart) throws GroceryException{
        List<Long> itemIds = cart.stream().map(e -> e.getItemId()).collect(Collectors.toList());
        return itemDao.findInIds(itemIds);
    }
    
    private Integer getQuantity(List<ItemQuantity> cart, Long id) throws GroceryException{
        Optional<ItemQuantity> item = cart.stream().filter(e -> id.equals(e.getItemId())).findFirst();
        if (item.isPresent())
            return item.get().getQuantity();
        throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ITEM_ID);
    }
    
    private Double getPrice(List<Item> items, Long id) throws GroceryException{
        Optional<Item> item = items.stream().filter(e -> id.equals(e.getId())).findFirst();
        if (item.isPresent())
            return item.get().getDiscountedPrice();
        throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ITEM_ID);
    }
}
