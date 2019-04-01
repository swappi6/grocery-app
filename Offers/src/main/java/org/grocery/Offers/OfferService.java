package org.grocery.Offers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferService {

	@Autowired
	OfferDao offerDao;
	
	
	 public List<Offer> getValidOffers() {
		 long millis=System.currentTimeMillis();
		 Date date = new Date(millis);
         return offerDao.findAllValid(date);
      }
}
