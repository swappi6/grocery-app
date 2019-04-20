package org.grocery.User;

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
@Table(name="Address")
@NamedQueries(
		{
		@NamedQuery(
					name="Address.findByAll",
					query="SELECT e FROM Address"
					),
		@NamedQuery(
				name ="Address.findByUserId",
				query ="SELECT e FROM Address"
				+"where e.userId= :userId"
				),	
		@NamedQuery(
				name ="Address.findById",
				query ="SELECT e FROM Address"
				+"where e.id = :id"
				)
		}
		)
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="userId",nullable=false)
	private Long userId;
	
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
	
	@Column(name="line1",nullable=false)
	private String line1;
	
	@Column(name="line2",nullable=false)
	private String line2;
	
	@Column(name="line3",nullable=true)
	private String line3;
	
	@Column(name="pincode",nullable=false)
	private Long pincode;
}
