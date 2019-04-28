package org.grocery.Address;

import java.util.List;

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
	

	
}
