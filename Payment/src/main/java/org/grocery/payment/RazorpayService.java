package org.grocery.payment;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayService implements PaymentService {
    
    RazorpayClient razorpayClient;
    
    public RazorpayService(){
        try {
            razorpayClient = new RazorpayClient("rzp_test_YFUGlXDXzm6HWg", "e7G33T7cuqjmjnFOG5T02NOP") ;
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String createOrder(Double amount) throws RazorpayException{
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        Order order = razorpayClient.Orders.create(options);
        return order.get("id");
    }

}