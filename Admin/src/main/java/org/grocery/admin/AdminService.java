package org.grocery.admin;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.CacheService;
import org.grocery.Utils.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminService {
    @Autowired
    RandomGenerator randomGenerator;
    @Autowired
    private CacheService cacheService;
    private static Integer expiry = 300;
    @Autowired
    AdminDao adminDao;

    public void createUser(AdminProfile adminProfile) throws GroceryException {
        Admin admin = new Admin();
        Admin user = adminDao.findByEmail(adminProfile.getEmail());
        if (user != null) {
            throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(), GroceryErrors.USER_PRESENT);
        }
        admin.setCountryCode(adminProfile.getCountryCode());
        admin.setEmail(adminProfile.getEmail());
        admin.setFirstName(adminProfile.getFirstName());
        admin.setLastName(adminProfile.getLastName());
        admin.setPassword(adminProfile.getPassword());
        admin.setMobileNo(adminProfile.getMobileNo());
        admin.setRole(adminProfile.getRole());
        adminDao.create(admin);
    }

    public AdminProfile login(LoginProfile loginDetails) throws GroceryException {
        Admin user = adminDao.findByEmail(loginDetails.getUsername());
        if (user == null)
            throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(), GroceryErrors.INVALID_USER);
        if (!user.getPassword().equals(loginDetails.getPassword()))
            throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(), GroceryErrors.INVALID_PASSWORD);
        AdminProfile profile = new AdminProfile();
        profile.setEmail(user.getEmail());
        profile.setCountryCode(user.getCountryCode());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setMobileNo(user.getMobileNo());
        String token = randomGenerator.generateUUID();
        cacheService.setValueWithExpiry(token, String.valueOf(user.getId()), expiry);
        profile.setToken(token);
        return profile;

    }

    public void logout(String token) throws GroceryException {
        cacheService.deleteKeys(token);
    }

    public Role getRoleofUser(long id) throws GroceryException {
        Optional<Admin> admin = adminDao.findById(id);
        if (!admin.isPresent())
            throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(), GroceryErrors.INVALID_USER);
        return admin.get().getRole();
    }
}
