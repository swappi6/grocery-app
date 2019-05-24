package org.grocery.item;

import java.util.List;
import java.util.Optional;

import org.grocery.category.Category;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class ItemDao extends AbstractDAO<Item> {

    private static SessionFactory factory;
    public ItemDao(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    public ItemDao() {
        super(factory);
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Item create(Item item) {
        return persist(item);
    }
    
    public Item update(Item item) {
        return persist(item);
    }
    
    public void delete(Item item) {
        currentSession().delete(item);
        currentSession().flush();
   }

    public List<Item> findAll() {
        return list(namedQuery("Item.findAll"));
    }
    
    public List<Item> findByCategory(Long parent) {
        return list(namedQuery("Item.findByCategory")
                .setParameter("category", parent));
    }
    
    public List<Item> searchByName(String searchKeyword) {
        return list(namedQuery("Item.searchByName")
                .setParameter("name", "%"+searchKeyword+"%"));
    }
    
    public List<Item> searchByDescription(String searchKeyword) {
        return list(namedQuery("Item.searchByDescription")
                .setParameter("description", "%"+searchKeyword+"%"));
    }
    
    public List<Item> findInIds(List<Long> itemIds) {
        return list(namedQuery("Item.findInIds")
                .setParameterList("itemIds", itemIds));
    }

}
