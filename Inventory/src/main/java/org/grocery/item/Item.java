package org.grocery.item;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.grocery.category.Category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
                        + "where m.category.id = :category"
        ),
        @NamedQuery(
                name = "Item.searchByName",
                query = "SELECT m FROM Item m "
                        + "where m.name like :name"
        ),
        @NamedQuery(
                name = "Item.searchByDescription",
                query = "SELECT m FROM Item m "
                        + "where m.description like :description"
        ),
        @NamedQuery(
                name = "Item.findInIds",
                query = "SELECT m FROM Item m "
                        + "where m.id IN (:itemIds)"
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
    
    @Column(name = "image_url", nullable = true)
    private String imageUrl;
    
    @Column(name = "price", nullable = true)
    private Double price;
    
    @Column(name = "discountedPrice", nullable = true)
    private Double discountedPrice;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    
    @ManyToOne()
    @JoinColumn(name= "category_id")
    private Category category;
    
    @Column(name= "subscribable",nullable = false)
    private Boolean subscribable;
    
    @Column(name= "least_count",nullable = false)
    private String leastCount;

    public Item() {
    }
    
    public Item(Long id) {
        this.id = id;
    }
    
    public Item(Long id, String name, String description, String imageUrl) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    
    @Override
    public boolean equals(Object that) {
        if (that == null)
          return false;
        if (that instanceof Item)
          return this.equals((Item)that);
        return false;
    }
    
    public boolean equals(Item that) {
        if (that == null)
          return false;
        if (!this.id.equals(that.id))
          return false;
        return true;
    }
    
    @Override
      public int hashCode() {
        return this.id.hashCode();
      }

}