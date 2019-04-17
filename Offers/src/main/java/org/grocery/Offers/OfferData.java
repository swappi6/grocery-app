package org.grocery.Offers;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferData {

	@NotNull
	private String name;
	
	@NotNull
	private OfferType type;
	
	@NotNull
	private Date expiryDate;
	private String encodedImage;
	
	@NotNull
	private Boolean active;

	@NotNull
	private Integer minAmount;

	@NotNull
	private Integer value;

	@NotNull
	private String description;
}
