package org.grocery.admin;

import org.grocery.Error.BuseaseException;
import org.grocery.Utils.CacheService;
import org.grocery.Utils.RandomGeneratorImpl;
import org.grocery.Utils.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminService {
	    @Autowired
	    RandomGeneratorImpl randomGeneratorImpl;
	    @Autowired
	    private CacheService cacheService;
	    private static Integer expiry = 300;
	    @Autowired
	    AdminDao adminDao;
	    
	    
	    public void createUser(AdminProfile adminProfile) throws BuseaseException{
	    	Admin admin =  new Admin();
	    	admin.setCountryCode(adminProfile.getCountryCode());
	    	admin.setEmail(adminProfile.getEmail());
	    	admin.setFirstName(adminProfile.getFirstName());
	    	admin.setLastName(adminProfile.getLastName());
	    	admin.setPassword(adminProfile.getPassword());
	    	admin.setMobileNo(adminProfile.getMobileNo());
	    	adminDao.create(admin);
	    	
	        /*
	        Optional<Admin> admin = adminDao.findById(userId);
	        if (!user.isPresent()) throw new BuseaseException(Response.Status.BAD_REQUEST.getStatusCode(),BuseaseErrors.INVALID_USER);
	        user.get().setEmail(userProfile.getEmail());
	        user.get().setEmail(userProfile.getEmail());
	        user.get().setFirstName(userProfile.getFirstName());
	        user.get().setLastName(userProfile.getLastName());
	        userDao.update(user.get());
	        */
	    }
	    
}
