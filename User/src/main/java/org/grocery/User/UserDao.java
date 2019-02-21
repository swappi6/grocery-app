package org.grocery.User;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class UserDao extends AbstractDAO<User> {

    private static SessionFactory factory;
    public UserDao(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    public UserDao() {
        super(factory);
    }
     
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public User create(User user) {
        return persist(user);
    }
    
    public User update(User user) {
        return persist(user);
    }

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

     public User findByMobileNo(String mobileNo) {
         return (User) namedQuery("User.findByMobile")
                .setParameter("mobileNo", mobileNo)
                .uniqueResult();
         
      }
}
