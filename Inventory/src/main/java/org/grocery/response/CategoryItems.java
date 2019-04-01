package org.grocery.response;

import java.util.List;

import org.grocery.category.Category;
import org.grocery.item.Item;

import lombok.Data;

@Data
public class CategoryItems {
    private List<Item> items;
    private List<Category> subCategories;

}
