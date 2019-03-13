package org.grocery.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemService {
    
    @Autowired
    ItemDao itemDao;
    
    public List<Item> getAllItems() throws Exception {
        return itemDao.findAll();
    }
    
    public List<Item> findByCategory(String parent) throws Exception {
        return itemDao.findByCategory(parent);
    }
}
