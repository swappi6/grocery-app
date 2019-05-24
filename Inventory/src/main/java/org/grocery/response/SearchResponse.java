package org.grocery.response;

import java.util.Set;

import org.grocery.item.Item;

import lombok.Data;

@Data
public class SearchResponse {
    private Set<Item> items;
}
