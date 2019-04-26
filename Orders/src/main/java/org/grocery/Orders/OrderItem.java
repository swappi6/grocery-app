package org.grocery.Orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "order_item")
//@NamedQueries(
//		{
//			@NamedQuery(
//					name = "order_item.findById",
//					query = "select e from order_item e "
//							+"where e.order.id = :order"
//					)
//		}
//		
//		
//		
//		)
public class OrderItem {
		
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name= "orderId")
	private Order order;
	
	@Column(name = "itemId" , nullable = false)
	private long itemId;
	
	@Column(name = "quantity" , nullable = false)
	private long quantity;

		
}
