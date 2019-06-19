package org.grocery.Subscription;

import io.dropwizard.hibernate.AbstractDAO;
import org.grocery.admin.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionDao extends AbstractDAO<Subscription> {


    private static SessionFactory factory;

    public SubscriptionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.factory = sessionFactory;
    }

    public SubscriptionDao() { super(factory); }

    public Optional<Subscription> findById(Long id) {
        Session session = factory.openSession();
        Optional<Subscription> subscriptionOptional =  Optional.ofNullable(session.get(Subscription.class, id));
        session.flush();
        return subscriptionOptional;
    }

    public Subscription create(Subscription subscription) { return persist(subscription); }

    public Subscription update(Subscription subscription) { return persist(subscription); }

    public List<Subscription> findByUser(long userId) {
        return list(namedQuery("Subscription.findByUserId")
                .setParameter("userId", userId));
    }
}
