package org.grocery.category;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.grocery.item.Item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
@NamedQueries(
    {
        @NamedQuery(
            name = "Category.findAll",
            query = "SELECT m FROM Category m"
        ),
        @NamedQuery(
                name = "Category.findParentCategories",
                query = "SELECT m FROM Category m "
                        + "where m.parent is null"
        ),
        @NamedQuery(
                name = "Category.findByCategory",
                query = "SELECT m FROM Category m "
                        + "where m.parent.id = :parent"
        )
    }
)
public class Category {
    
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
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    
    @ManyToOne()
    @JoinColumn(name="parent")
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy="parent")
    private List<Category> subCategories;
    
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Item> items;

    public Category(Long id) {
        this.id = id;
    }
    
    public Category() {
    }
    
    public Category(Long id, String name, String description, String imageUrl) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}