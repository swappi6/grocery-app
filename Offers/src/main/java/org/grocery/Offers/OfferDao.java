package org.grocery.Offers;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class OfferDao extends AbstractDAO<Offer> {

  private static SessionFactory factory;
 
    public OfferDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}


    public OfferDao() {
        super(factory);
    }
     
    public Optional<Offer> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Offer create(Offer offer) {
        return persist(offer);
    }
    
    public List<Offer> findAll() {
        return list(namedQuery("Offer.findAll"));
    }

     public Offer findByValid(String date , Integer value) {
         return (Offer) namedQuery("Offer.findByValid")
                .setParameter("date", date)
                .setParameter("value",value);
      }
     
     public Offer findByValue(String date , Integer value) {
         return (Offer) namedQuery("Offer.findByValue")
                .setParameter("date", date)
                .setParameter("value",value);
      }
}