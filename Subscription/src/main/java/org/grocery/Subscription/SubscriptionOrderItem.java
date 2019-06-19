package org.grocery.Subscription;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "subscription_order_items")
@NamedQueries(
        {}
)
public class SubscriptionOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private long id;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "subscription_order_id")
    private SubscriptionOrder subscriptionOrder;

    @Column(name = "item_id", nullable = false)
    private long itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
