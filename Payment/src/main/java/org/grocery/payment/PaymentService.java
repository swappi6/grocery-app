package org.grocery.payment;

import org.springframework.stereotype.Component;

@Component
public interface PaymentService {
    
    public String createOrder(Double amount) throws Exception;

}
