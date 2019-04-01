package org.grocery.response;

import java.util.List;

import org.grocery.Offers.Offer;
import org.grocery.category.Category;

import lombok.Data;

@Data
public class ExploreResponse {
    private List<Category> categories;
    private List<Offer> offers;
}
