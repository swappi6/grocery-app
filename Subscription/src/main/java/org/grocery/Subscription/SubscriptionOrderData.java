package org.grocery.Subscription;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class SubscriptionOrderData {

    @NotNull
    private long id;
    private Timestamp deliveryDate;
    private SubscriptionOrderStatus orderStatus;

}
