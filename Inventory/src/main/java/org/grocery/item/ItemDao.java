package org.grocery.item;

import java.util.List;
import java.util.Optional;

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

    public Item create(Item category) {
        return persist(category);
    }
    
    public Item update(Item category) {
        return persist(category);
    }

    public List<Item> findAll() {
        return list(namedQuery("Item.findAll"));
    }
    
    public List<Item> findByCategory(String parent) {
        return (List<Item>) namedQuery("Item.findByCategory")
                .setParameter("parent", parent);
    }

}
