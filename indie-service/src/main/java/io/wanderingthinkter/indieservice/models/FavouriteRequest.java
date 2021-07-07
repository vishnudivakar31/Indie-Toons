package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteRequest {
    private Long artistID;
    private List<FavouriteMap> favourites;
}
