package org.grocery.Offers;

import java.sql.Date;
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
		this.factory = sessionFactory;
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
    
    public Offer update(Offer offer) {
    	return persist(offer);
    }
    
    public void delete(Offer offer) {
        currentSession().delete(offer);
    }
    
    public List<Offer> findAll() {
        return list(namedQuery("Offer.findAll"));
    }

    public List<Offer> findAllValid(Date date) {
         return list(namedQuery("Offer.findAllValid")
        		.setParameter("date", date));
    }
     
}