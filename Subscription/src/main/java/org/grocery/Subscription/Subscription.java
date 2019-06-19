package org.grocery.Subscription;

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
@Table(name = "subscriptions")
@NamedQueries(
        {
                @NamedQuery(
                        name = "Subscription.findByUserId",
                        query = "SELECT e FROM Subscription e "
                                +"where e.userId = :userId"
                )
        }
)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private long id;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name = "total_orders")
    private Integer totalSubscriptionOrders;

    @Column(name = "frequency")
    private String frequency;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscription")
    private List<SubscriptionOrder> subscriptionOrders;

    public Subscription() {}

}
