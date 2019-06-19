package org.grocery.Subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "subscription_orders")
@NamedQueries(
        {}
)
public class SubscriptionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private long id;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "delivery_date", nullable = false)
    private Timestamp deliveryDate;

    @Column(name = "order_status")
    private SubscriptionOrderStatus orderStatus;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "price")
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscriptionOrder")
    private List<SubscriptionOrderItem> subscriptionOrderItems;


}
