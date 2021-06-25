package io.wanderingthinkter.indietoonsusernamangement.repositories;

import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUsername(String username);
}
