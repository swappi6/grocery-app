package org.grocery.Offers;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.EncodedStringHelper;
import org.grocery.Utils.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferService {

	@Autowired
	OfferDao offerDao;
	
	@Autowired
    EncodedStringHelper encodedStringHelper;
	
	@Autowired
	FileStore store;
	 public List<Offer> getAllOffers() throws Exception {
	        List<Offer> offers = offerDao.findAll();
	        return offers;
	    }
	
	 public List<Offer> getValidOffers() {
		 long millis=System.currentTimeMillis();
		 Date date = new Date(millis);
         return offerDao.findAllValid(date);
      }
	 
	 public void createOffer(OfferData offerData) throws GroceryException{
	        Offer offer = new Offer();
	        offer.setName(offerData.getName());
	        offer.setType(offerData.getType());
	        offer.setExpiryDate(offerData.getExpiryDate());
	        offer.setActive(offerData.getActive());
	        offer.setMinAmount(offer.getMinAmount());
	        offer.setValue(offerData.getValue());
	        offer.setDescription(offer.getDescription());
	        InputStream inputStream =encodedStringHelper. getInputStream(offerData.getEncodedImage());
	        if (inputStream != null) {
	            String imageUrl = store.upload(offerData.getName(), Constants.Buckets.OFFER, inputStream);
	            offer.setImageUrl(imageUrl);
	        }
	        offerDao.create(offer);
	    }
	 
	 public void delete(Long id){
	        offerDao.delete(new Offer(id));
	    }
	 
	 public void updateOffer(OfferData offerData, Long offerId) throws GroceryException{
	        Optional<Offer> optionalOffer = offerDao.findById(offerId);
	        if (!optionalOffer.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ITEM_ID);
	        Offer offer = optionalOffer.get();
	        if (offerData.getName() != null)
	            offer.setName(offerData.getName());
	        if (offerData.getType() != null)
	            offer.setType(offerData.getType());
	        if (offerData.getExpiryDate() != null)
	            offer.setExpiryDate(offerData.getExpiryDate());
	        if (offerData.getActive() != null)
	            offer.setActive(offerData.getActive());
	        if (offerData.getMinAmount() != null)
	            offer.setMinAmount(offerData.getMinAmount());
	        if (offerData.getValue() != null)
	        	offer.setValue(offerData.getValue());
	        if (offerData.getDescription() != null)
	        	offer.setDescription(offerData.getDescription());
	        InputStream inputStream =encodedStringHelper. getInputStream(offerData.getEncodedImage());
	        if (inputStream != null) {
	            String offerUrl = store.upload(offerData.getName(), Constants.Buckets.OFFER, inputStream);
	            offer.setImageUrl(offerUrl);
	        }
	       
	        offerDao.update(offer);
	    }
}
