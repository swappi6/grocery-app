package org.grocery.Address;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressService {
	
	@Autowired
	AddressDao addressDao;
	
	public List<Address> getAllAddress() throws Exception {
		List<Address> address = addressDao.findAll();
		return address;
	}
	
	public List<Address> getByUserId(Long userid) throws Exception {
		List<Address> address = addressDao.findByUserId(userid);
		return address;
	}
	
	public void createAddress(AddressData addressData)throws GroceryException{
		Address address = new Address();
		User user = new User();
		user.setId(addressData.getUserid());
		address.setUser(user);
		address.setName(addressData.getName());
		address.setType(addressData.getType());
		address.setLatitude(addressData.getLatitude());
		address.setLongitude(addressData.getLongitude());
		address.setPhoneNumber(addressData.getPhoneNumber());
		address.setPincode(addressData.getPincode());
		address.setGoogleAddress(addressData.getGoogleAddress());
		address.setHouseNumber(addressData.getHouseNumber());
		address.setRoadNumber(addressData.getRoadName());
		address.setCity(addressData.getCity());
		address.setState(addressData.getState());
		address.setLandmark(addressData.getLandmark());
		addressDao.create(address);
	}
	
	public void delete(Long id) {
		addressDao.delete(new Address(id));
	}
	
	public void updateAddress(AddressData addressData, Long addressId) throws GroceryException{
		Optional<Address> optionalAddress = addressDao.findBYId(addressId);
		if(!optionalAddress.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ADDRESS_ID);
	        Address address = optionalAddress.get();
	    if(addressData.getName() !=null)
	    	address.setName(addressData.getName());
	    if(addressData.getType() !=null)
	    	address.setType(addressData.getType());
	    if(addressData.getLatitude() !=null)
	    	address.setLatitude(addressData.getLatitude());
	    if(addressData.getLongitude() !=null)
	    	address.setLongitude(addressData.getLongitude());
	    if(addressData.getGoogleAddress() !=null)
	    	address.setGoogleAddress(addressData.getGoogleAddress());
	    if(addressData.getPhoneNumber() !=null)
	    	address.setPhoneNumber(addressData.getPhoneNumber());
	    if(addressData.getPincode() !=null)
	    	address.setPincode(addressData.getPincode());
	    if(addressData.getHouseNumber() !=null)
	    	address.setHouseNumber(addressData.getHouseNumber());
	    if(addressData.getRoadName() !=null)
	    	address.setRoadNumber(addressData.getRoadName());
	    if(addressData.getCity() !=null)
	    	address.setCity(addressData.getCity());
	    if(addressData.getState() !=null)
	    	address.setState(addressData.getState());
	    if(addressData.getLandmark() !=null)
	    	address.setLandmark(addressData.getLandmark());
	    addressDao.update(address);
	    
	}
	
	
	
	
}
