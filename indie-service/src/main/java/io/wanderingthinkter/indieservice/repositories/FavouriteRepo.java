package io.wanderingthinkter.indieservice.repositories;

import io.wanderingthinkter.indieservice.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepo extends JpaRepository<Favourite, Long> {
    List<Favourite> findAllByArtistID(Long artistID);
}
