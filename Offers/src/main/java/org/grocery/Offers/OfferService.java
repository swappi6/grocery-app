package org.grocery.Offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferService {

	@Autowired
	 OfferDao offerDao;
	
}
