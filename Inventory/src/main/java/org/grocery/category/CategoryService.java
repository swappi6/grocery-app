package org.grocery.category;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {
    
    @Autowired
    CategoryDao categoryDao;
    
    @Autowired
    FileStore store;
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public List<Category> getParentCategories() throws Exception {
        return categoryDao.findParentCategories();
    }
    
    public List<Category> findByCategory(String parent) throws Exception {
        return categoryDao.findByCategory(parent);
    }
    
    public void updateCategory(Long categoryId, CategoryData categoryData) {
        Optional<Category> category = categoryDao.findById(categoryId);
//        if (!category.isPresent()) throw new Exception();
//        user.get().setEmail(userProfile.getEmail());
//        user.get().setFirstName(userProfile.getFirstName());
//        user.get().setLastName(userProfile.getLastName());
//        userDao.update(user.get());
        
        
    }
    
    public void createCategory(CategoryData categoryData, InputStream inputStream) throws GroceryException{
        Category cat = new Category();
        cat.setName(categoryData.getName());
        cat.setDescription(categoryData.getDescription());
        cat.setParent(categoryData.getParent());
        String imageUrl = store.upload(categoryData.getImage(), categoryData.getName(), Constants.Buckets.CATEGORY, inputStream);
        cat.setImageUrl(imageUrl);
        categoryDao.create(cat);
    }
}
