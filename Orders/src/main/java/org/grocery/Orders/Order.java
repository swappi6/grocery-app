package org.grocery.Orders;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="orders")
@NamedQueries(
		{
			/*@NamedQuery(
					name = "Order.findAll",
					query = "SELECT e FROM Orders e"
					),*/
			@NamedQuery(
					name = "Order.findByUserId",
					query = "SELECT e FROM Order e "
							+"where e.userId = :userId"
					),
			/*@NamedQuery(
					name = "Order.findByAddressId",
					query = "SELECT e FROM Orders e"
							+"where e.address.id = :addressId"
					),*/
			@NamedQuery(
					name = "Order.findById",
					query = "SELECT e FROM Order e "
							+" where e.id = :id"
					),
			@NamedQuery(
					name = "Order.findByDate",
					query = "Select e from Order e "
							+"where e.createdAt = :createdAt"  //created_at is timestamp how will it give date.//
					)
		}
	)


public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	private long id;


	@Column(name = "user_id" , nullable = false)
	private long userId;

	@Column(name = "address_id" , nullable = false)
	private long addressId;

	@Column(name = "actual_price" , nullable = true)
	private double actualPrice;

	@Column(name = "discounted_price" , nullable = true)
	private double discountedPrice;

	@Column(name = "offer_id" , nullable = true)
	private long offerId;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at" , nullable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at" , nullable=false)
	private Timestamp updatedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status" , nullable = true)
	private OrderStatus status;

	@OneToMany(cascade=CascadeType.ALL, mappedBy = "order")
	private List<OrderItem> items;

	public Order(Long id) {

		this.id=id;
	}

	public Order() {

	}



}



