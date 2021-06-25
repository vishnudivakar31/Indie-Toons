package io.wanderingthinkter.indietoonsusernamangement.services;

import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import io.wanderingthinkter.indietoonsusernamangement.repositories.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepo artistRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Artist createArtist(Artist artist) {
        Date date = new Date();
        artist.setPassword(bCryptPasswordEncoder.encode(artist.getPassword()));
        artist.setCreatedDate(date);
        artist.setUpdatedDate(date);
        artist.setActive(true);
        return artistRepo.save(artist);
    }

    public Optional<Artist> getArtist(Long id) {
        return artistRepo.findById(id);
    }

    public Optional<Artist> getArtist(String username) {
        return artistRepo.findByUsername(username);
    }
}
