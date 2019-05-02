package org.grocery.Address;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressData {
	
	@NotNull
	private Long userid;
	
	@NotNull
	private String name;
	
	@NotNull
	private String houseNumber;
	
	@NotNull
	private String roadName;
	
	private String landmark;
	
	@NotNull
	private String city;
	
	@NotNull
	private String state;
	
	@NotNull
	private AddressType type;
	
	@NotNull
	private String latitude;
	
	@NotNull
	private String longitude;
	
	
	@NotNull
	private Long pincode;
	
	@NotNull
	private Long phoneNumber;
	
	@NotNull
	private String googleAddress;
	
	
}
