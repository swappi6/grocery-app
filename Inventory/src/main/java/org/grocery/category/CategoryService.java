package org.grocery.category;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.EncodedStringHelper;
import org.grocery.Utils.FileStore;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {
    
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    FileStore store;
    @Autowired
    EncodedStringHelper encodedStringHelper;
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public List<Category> getParentCategories() throws Exception {
        return categoryDao.findParentCategories();
    }
    
    public List<Category> findByCategory(Long parent) throws Exception {
        return categoryDao.findByCategory(parent);
    }
    
    public void delete(Long id) throws GroceryException{
        Optional<Category> optionalCategory = categoryDao.findById(id);
        if (!optionalCategory.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_CATEGORY_ID);
        Category category = optionalCategory.get();
        store.delete(category.getName(), Constants.Buckets.CATEGORY); 
        categoryDao.delete(category);
    }
    
    public void updateCategory(CategoryData categoryData, Long categoryId) throws GroceryException{
        Optional<Category> optionalCategory = categoryDao.findById(categoryId);
        if (!optionalCategory.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_CATEGORY_ID);
        Category category = optionalCategory.get();
        if (categoryData.getName() != null) {
            store.rename(category.getName(), Constants.Buckets.CATEGORY, categoryData.getName());
            category.setName(categoryData.getName());
        }
        if (categoryData.getDescription() != null)
            category.setDescription(categoryData.getDescription());
        InputStream inputStream =encodedStringHelper. getInputStream(categoryData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(categoryData.getName(), Constants.Buckets.CATEGORY, inputStream);
            category.setImageUrl(imageUrl);
        }
        if (categoryData.getParent() != null) {
            Category cat = new Category();
            cat.setId(categoryData.getParent());
            category.setParent(cat);
        }
        categoryDao.update(category);
    }

    public void createCategory(CategoryData categoryData) throws GroceryException{
        Category cat = new Category();
        cat.setName(categoryData.getName());
        cat.setDescription(categoryData.getDescription());
        if(categoryData.getParent() != null)
        	cat.setParent(new Category(categoryData.getParent()));
        InputStream inputStream =encodedStringHelper. getInputStream(categoryData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(categoryData.getName(), Constants.Buckets.CATEGORY, inputStream);
            cat.setImageUrl(imageUrl);
        }
        try {
            categoryDao.create(cat);
        } catch (ConstraintViolationException e) {
            throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(), new Object[]{e.getCause().getMessage()});
        }
                
    }
    
}
