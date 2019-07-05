package org.grocery.Address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.grocery.User.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="address")
@NamedQueries(
		{
		@NamedQuery(
					name="Address.findAll",
					query="SELECT e FROM Address e"
					),
		@NamedQuery(
					name ="Address.findByUserid",
					query ="SELECT e FROM Address e "
							+ "where e.user = :user"
				),	
		@NamedQuery(
					name ="Address.findById",
					query ="SELECT e FROM Address e "
							+"where e.id = :id"
				)
		}
		)
public class Address {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name= "user_id")
	@ManyToOne()
    @JsonIgnore
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type",nullable=false)
	private AddressType type;
	
	@Column(name="latitude",nullable=false)
	private String latitude;
	
	@Column(name="longitude",nullable=false)
	private String longitude;
	
	@Column(name="phoneNumber",nullable=false)
	private Long phoneNumber;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="houseno",nullable=false)
	private String houseNumber;
	
	@Column(name="roadno",nullable=false)
	private String roadNumber;
	
	@Column(name="city",nullable=false)
	private String city;
	
	@Column(name="state",nullable=false)
	private String state;
	
	@Column(name="landmark",nullable=true)
	private String landmark;
	
	@Column(name="pincode",nullable=false)
	private Long pincode;
	
	@Column(name="googleAddress",nullable=false)
	private String googleAddress;

	public Address() {
		
	}
	public Address(Long id) {
	    this.id = id;
	}
}
