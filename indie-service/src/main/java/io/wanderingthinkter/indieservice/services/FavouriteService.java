package io.wanderingthinkter.indieservice.services;

import io.wanderingthinkter.indieservice.models.Favourite;
import io.wanderingthinkter.indieservice.models.FavouriteRequest;
import io.wanderingthinkter.indieservice.models.FavouriteType;
import io.wanderingthinkter.indieservice.repositories.FavouriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

    @Autowired
    private FavouriteRepo favouriteRepo;

    public List<FavouriteType> getFavouriteTypes() {
        return Arrays.asList(FavouriteType.values());
    }

    public List<Favourite> saveFavourites(FavouriteRequest favouriteRequest) {
        List<Favourite> favourites = favouriteRequest
                .getFavourites()
                .parallelStream()
                .map(favouriteMap -> {
                    Favourite favourite = new Favourite();
                    favourite.setFavouriteType(favouriteMap.getFavouriteType());
                    if (favouriteMap.getFavouriteType() == FavouriteType.ARTIST) {
                        favourite.setFavouriteArtistID(favouriteMap.getFavouriteArtistID());
                    } else {
                        favourite.setCategoryType(favouriteMap.getCategoryType());
                    }
                    favourite.setCreatedDate(new Date());
                    favourite.setArtistID(favouriteRequest.getArtistID());
                    return favourite;
                })
                .collect(Collectors.toList());
        return favouriteRepo.saveAll(favourites);
    }

    public List<Favourite> getAllFavourites(Long artistID) {
        return favouriteRepo.findAllByArtistID(artistID);
    }
}
