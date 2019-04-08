package org.grocery.category;

import java.util.List;
import java.util.Optional;

import org.grocery.item.Item;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class CategoryDao extends AbstractDAO<Category> {

    private static SessionFactory factory;
    public CategoryDao(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    public CategoryDao() {
        super(factory);
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Category create(Category category) {
        return persist(category);
    }
    
    public Category update(Category category) {
        return persist(category);
    }
    
    public void delete(Category category) {
         currentSession().delete(category);
         currentSession().flush();
    }

    public List<Category> findAll() {
        return list(namedQuery("Category.findAll"));
    }
    
    public List<Category> findParentCategories() {
        return list(namedQuery("Category.findParentCategories"));
    }
    
    public List<Category> findByCategory(Long parent) {
        return list(namedQuery("Category.findByCategory")
                .setParameter("parent", parent));
    }

}
