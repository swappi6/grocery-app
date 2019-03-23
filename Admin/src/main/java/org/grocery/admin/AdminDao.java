package org.grocery.admin;
import java.util.List;
import java.util.Optional;

import org.grocery.admin.Admin;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class AdminDao extends AbstractDAO<Admin>{
	private static SessionFactory factory;
	public AdminDao(SessionFactory factory) {		
        super(factory);
        this.factory = factory;
    }
	
	 public AdminDao() {
	        super(factory);
	 }
	 
	    public Optional<Admin> findById(Long id) {
	        return Optional.ofNullable(get(id));
	    }

	    public Admin create(Admin admin) {
	        return persist(admin);
	    }
	    
	    public Admin update(Admin admin) {
	        return persist(admin);
	    }

	    public List<Admin> findAll() {
	        return list(namedQuery("Admin.findAll"));
	    }

	     public Admin findByEmail(String email) {
	         return (Admin) namedQuery("Admin.findByEmail")
	                .setParameter("email", email)
	                .uniqueResult();	         
	      }
}
