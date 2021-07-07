package io.wanderingthinkter.indieservice.services;

import io.wanderingthinkter.indieservice.models.FavouriteType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FavouriteService {

    public List<FavouriteType> getFavouriteTypes() {
        return Arrays.asList(FavouriteType.values());
    }

}
