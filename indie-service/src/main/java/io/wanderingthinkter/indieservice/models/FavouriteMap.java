package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteMap {
    private FavouriteType favouriteType;
    private Category category;
    private Long favouriteArtistID;
}
