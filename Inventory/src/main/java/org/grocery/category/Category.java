package org.grocery.category;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
                        + "where m.parent = :parent"
        )
    }
)
public class Category {
    
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "parent", nullable = true)
    private String parent;
    
    @Column(name = "image_url", nullable = true)
    private String imageUrl;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

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