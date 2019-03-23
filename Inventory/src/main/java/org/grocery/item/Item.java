package org.grocery.item;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "items")
@NamedQueries(
    {
        @NamedQuery(
            name = "Item.findAll",
            query = "SELECT m FROM Item m"
        ),
        @NamedQuery(
                name = "Item.findByCategory",
                query = "SELECT m FROM Item m "
                        + "where m.parent = :parent"
        )
    }
)
public class Item {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "parent", nullable = true)
    private String parent;
    
    @Column(name = "image_url", nullable = true)
    private String imageUrl;
    
    @Column(name = "price", nullable = true)
    private Double price;
    
    @Column(name = "discounted_price", nullable = true)
    private String discountedPrice;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    public Item() {
    }
    
    public Item(Long id, String name, String description, String imageUrl) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}