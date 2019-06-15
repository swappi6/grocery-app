package org.grocery.Subscription;

import lombok.Data;
import org.grocery.item.Item;
import org.grocery.item.ItemDataWithQuantity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class SubscriptionData {

    @NotNull
    private long subscriptionId;
    private SubscriptionStatus subscriptionStatus;
    private long numOfSubscriptionsLeft;
    private long userId;
    private ArrayList<ItemDataWithQuantity> itemDataWithQuantities;
    private List<SubscriptionOrder> subscriptionOrders;
    private ArrayList<Integer> frequency;
}
