package org.grocery.Subscription;

import lombok.Data;
import org.grocery.item.ItemDataWithQuantity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubscriptionData {

    @NotNull
    private long subscriptionId;
    @NotNull
    private long numOfSubscriptionsLeft;
    @NotNull
    private long userId;
    private ArrayList<Integer> frequency;
}
