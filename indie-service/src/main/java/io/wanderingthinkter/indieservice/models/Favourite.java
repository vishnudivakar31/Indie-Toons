package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "favourites", uniqueConstraints = { @UniqueConstraint(columnNames = {"artistID", "favouriteArtistID", "categoryType"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, updatable = false)
    private Long artistID;
    private FavouriteType favouriteType;
    private Long favouriteArtistID;
    private CategoryType categoryType;
    @Column(nullable = false, updatable = false)
    private Date createdDate;
}
