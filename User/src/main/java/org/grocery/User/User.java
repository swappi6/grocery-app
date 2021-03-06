package org.grocery.User;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.grocery.Address.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NamedQueries(
    {
        @NamedQuery(
            name = "User.findAll",
            query = "SELECT m FROM User m"
        ),
        @NamedQuery(
                name = "User.findByMobile",
                query = "select e from User e "
                        + "where e.mobileNo = :mobileNo "
        ),
        @NamedQuery(
                name = "User.findById",
                query = "select e from User e "
                        + "where e.id = :id "
        )
    }
)
public class User {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;
    
    @Column(name = "email", nullable = true)
    private String email;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    
//    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    public User() {
    }
    
    public User(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }

    public User(String mobileNo, String countryCode, String firstName, String lastName, String email) {
        super();
        this.mobileNo = mobileNo;
        this.countryCode = countryCode;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}