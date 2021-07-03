package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "artist-id", nullable = false, updatable = false)
    private Long artistID;

    @Column(name = "category-type", nullable = false)
    private CategoryType categoryType;

    @Column(name = "category-name")
    private String categoryName;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;
}
