package org.grocery.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {
    
    @Autowired
    CategoryDao categoryDao;
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public List<Category> getParentCategories() throws Exception {
        return categoryDao.findParentCategories();
    }
    
    public List<Category> findByCategory(String parent) throws Exception {
        return categoryDao.findByCategory(parent);
    }
}
