package org.grocery.Subscription;

import lombok.Data;
import org.grocery.item.ItemQuantity;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class SubscriptionInputData {

    @NotNull
    private List<ItemQuantity> itemsWithQuantity;
    @NotNull
    private long userId;
    private Timestamp startDate;
    private ArrayList<Integer> frequency;
    private Integer numOfSubscriptions;

}

