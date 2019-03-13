package org.grocery.admin;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "admin_users")
@NamedQueries(
    {
        @NamedQuery(
            name = "User.findAll",
            query = "SELECT m FROM Admin m"
        ),
        @NamedQuery(
                name = "User.findByMobile",
                query = "select e from Admin e "
                        + "where e.mobileNo = :mobileNo "
        ),
        @NamedQuery(
                name = "User.findById",
                query = "select e from Admin e "
                        + "where e.id = :id "
        )
    }
)

public class Admin {
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;
    
    @Version
    @Column(name = "lock_version", nullable = false)
    private Integer lockVersion;
    
    @Column(name = "email", nullable = true)
    private String email;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    
    @Column(name = "password", nullable = true)
    private String password;

    public Admin() {
    }
    /*
    public Admin(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }
	*/
    public Admin(String mobileNo, String countryCode, String firstName, String lastName, String email,String Password) {
        super();
        this.mobileNo = mobileNo;
        this.countryCode = countryCode;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = Password; //TO BE HASHED.
    }

}
