package io.wanderingthinkter.indieservice.controllers;

import io.wanderingthinkter.indieservice.models.Favourite;
import io.wanderingthinkter.indieservice.models.FavouriteRequest;
import io.wanderingthinkter.indieservice.models.FavouriteType;
import io.wanderingthinkter.indieservice.services.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping
    public List<Favourite> getAllFavourites(@RequestParam Long artistID) {
        return favouriteService.getAllFavourites(artistID);
    }

    @GetMapping("/types")
    public List<FavouriteType> getFavouriteTypes() {
        return favouriteService.getFavouriteTypes();
    }

    @PostMapping
    public List<Favourite> saveFavourites(@RequestBody FavouriteRequest favouriteRequest) {
        return favouriteService.saveFavourites(favouriteRequest);
    }

    @PutMapping("/remove")
    public List<Favourite> deleteFavourites(@RequestBody List<Favourite> favourites) {
        return favouriteService.deleteFavourites(favourites);
    }
}
