package org.grocery.category;

import java.io.InputStream;
import java.util.List;

import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.EncodedStringHelper;
import org.grocery.Utils.FileStore;
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
    
    public void delete(Long id){
        categoryDao.delete(new Category(id));
    }
    
    public void updateCategory(Long categoryId, CategoryData categoryData) throws GroceryException{
        Category cat = new Category();
        cat.setId(categoryId);
        cat.setName(categoryData.getName());
        cat.setDescription(categoryData.getDescription());
        cat.setParent(new Category(categoryData.getParent()));
        InputStream inputStream = encodedStringHelper.getInputStream(categoryData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(categoryData.getName(), Constants.Buckets.CATEGORY, inputStream);
            cat.setImageUrl(imageUrl);
        }
        categoryDao.update(cat);
    }

    public void createCategory(CategoryData categoryData) throws GroceryException{
        Category cat = new Category();
        cat.setName(categoryData.getName());
        cat.setDescription(categoryData.getDescription());
        cat.setParent(new Category(categoryData.getParent()));
        InputStream inputStream =encodedStringHelper. getInputStream(categoryData.getEncodedImage());
        if (inputStream != null) {
            String imageUrl = store.upload(categoryData.getName(), Constants.Buckets.CATEGORY, inputStream);
            cat.setImageUrl(imageUrl);
        }
        categoryDao.create(cat);
    }
}
