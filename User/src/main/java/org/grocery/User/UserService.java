package org.grocery.User;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Auth.AuthService;
import org.grocery.Auth.AuthTokens;
import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.CacheService;
import org.grocery.Utils.RandomGenerator;
import org.grocery.Utils.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    
    @Autowired
    RandomGenerator randomGenerator;
    @Autowired
    SmsService smsService;
    @Autowired
    private CacheService cacheService;
    private static Integer expiry = 300;
    private static String staticMessage = "Enter OTP <OTP> to login";
    @Autowired
    UserDao userDao;
    @Autowired
    AuthService authService;
    
    public void sendOtp(String mobileNo) {
        String otp = randomGenerator.generateOtp();
        System.out.println(otp);
        cacheService.setValueWithExpiry(mobileNo, otp, expiry);
        String message = staticMessage.replace("<OTP>", otp);
        smsService.send(message, mobileNo);
    }
    
    public AuthTokens validateOtp(String mobileNo, String otp, String deviceId) throws GroceryException{
        boolean isValidOtp = isValidOtp(mobileNo, otp);
        if (!isValidOtp) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_OTP);
        User user = userDao.findByMobileNo(mobileNo);
        if (null == user) {
            user = new User();
            user.setMobileNo(mobileNo);
            userDao.update(user);
        }
        Long userId = user.getId();
        AuthTokens tokens = authService.updateRefreshAndAccessToken(userId, deviceId);
        cacheService.deleteKeys(mobileNo);
        return tokens;
    }
    
    public void updateUser(Long userId, UserProfile userProfile) throws GroceryException{
        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_USER);
        user.get().setEmail(userProfile.getEmail());
        user.get().setFirstName(userProfile.getFirstName());
        user.get().setLastName(userProfile.getLastName());
        userDao.update(user.get());
    }
    
    public User getUser(Long userId) throws GroceryException{
        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_USER);
        return user.get();
//        UserProfile profile = new UserProfile();
//        profile.setFirstName(user.get().getFirstName());
//        profile.setLastName(user.get().getLastName());
//        profile.setEmail(user.get().getEmail());
//        return profile;
        
    }
    
    private boolean isValidOtp(String mobileNo, String otp) {
        String otpInCache = cacheService.getValue(mobileNo);
        return otp.equals(otpInCache);
    }
    
}
