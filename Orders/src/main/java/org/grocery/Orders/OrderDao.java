package org.grocery.Orders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.grocery.item.Item;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class OrderDao extends AbstractDAO<Order>{
	
	private static SessionFactory factory;

	public OrderDao(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.factory = sessionFactory;
	}
	
	public OrderDao() 
	{
		super(factory);
	}
	
	public Optional<Order> findById(Long val){
		return Optional.ofNullable(get(val));
	}	
	
	public Order create(Order order) {
		return persist(order);
	}
	
	public Order update(Order order) {
		return persist(order);
	}
	
	public void delete(Order order) {
		currentSession().delete(order);
	}
	
	/*public List<Order>findAll(){
		return list(namedQuery("Order.findAll"));
	}*/
	
	public List<Order>findByCreatedDate(Long millis){
		Date date = new Date(millis);
		return list(namedQuery("Order.findByCreatedDate")
				.setParameter("created_at", date));
	}
	
	public List<Order>findByUserId(long parent){
		return list(namedQuery("Order.findByUserId")
				.setParameter("userId", parent));
	}
	
	public List<Order> findInStatus(List<OrderStatus> status) {
        return list(namedQuery("Order.findInStatus")
                .setParameterList("status", status));
    }
	
	public List<Order>findByDeliveryDate(Long millis){
        Date date = new Date(millis);
        return list(namedQuery("Order.findByDeliveryDate")
                .setParameter("delivery_day", date));
    }
	

}
