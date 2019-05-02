package org.grocery.Address;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class AddressDao extends AbstractDAO<Address> {

	private static SessionFactory factory;
	public AddressDao(SessionFactory factory) {
		super(factory);
		this.factory = factory;
	}

	public AddressDao() {
		super(factory);
	}
	
	public Optional<Address> findBYId(Long id) {
		return Optional.ofNullable(get(id));
	}
	
	public Address create(Address address) {
		return persist(address);
	}
	
	public void delete(Address address) {
		 currentSession().delete(address);
	}
	
	
	
	public Address update(Address address) {
		return persist(address);
	}
	
	public List<Address> findAll() {
		return list(namedQuery("Address.findAll"));
	}
	
	public List<Address> findByUserId(Long userid) {
		return list(namedQuery("Address.findByUserId")
				.setParameter("userId", userid));
	}
}
