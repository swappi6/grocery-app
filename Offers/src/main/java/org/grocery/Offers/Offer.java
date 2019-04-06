package org.grocery.Offers;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="offers")
@NamedQueries(
	    {
	        @NamedQuery(
	            name = "Offer.findAll",
	            query = "SELECT e FROM Offer e"
	        ),
	        @NamedQuery(
	        		name = "Offer.findAllValid",
	        		query = "SELECT e FROM Offer e "
	        				+"where e.expiryDate <= :date AND e.active=true"
	        		),
	        @NamedQuery(
	                name = "Offer.findById",
	                query = "select e from Offer e "
	                        + "where e.id = :id "
	        )
	    }
	)

public class Offer {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

@Column(name = "name", nullable = false)
private String name;

@Enumerated(EnumType.STRING)
@Column(name = "type", nullable = false)
private OfferType type;

@Column(name = "expiry_date", nullable = false)
private Date expiryDate;

@Column(name= "image_url",nullable = true)
private String imageUrl;

@Column(name= "active",nullable = false)
private Boolean active;

@Column(name= "min_amount",nullable = false)
private int minAmount;

@Column(name= "value",nullable = false)
private int value;

@Column(name= "description",nullable = false)
private String description;

public Offer() {
	
}

public Offer(Long id) {
    this.id = id;
}
}


