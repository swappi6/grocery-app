package org.grocery.response;

import java.util.List;

import org.grocery.item.Item;

import lombok.Data;

@Data
public class SearchResponse {
    private List<Item> items;
}
