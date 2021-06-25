package io.wanderingthinkter.indietoonsusernamangement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import io.wanderingthinkter.indietoonsusernamangement.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("usermanagement/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @PostMapping()
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable Long id) throws JsonProcessingException {
        Optional<Artist> optionalArtist = artistService.getArtist(id);
        if (optionalArtist.isPresent()) {
            return optionalArtist.get();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not found. please sign in first");
    }

    @GetMapping()
    public Artist getArtist(@RequestParam String username) {
        Optional<Artist> optionalArtist = artistService.getArtist(username);
        if (optionalArtist.isPresent()) {
            return optionalArtist.get();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not found. please sign in first");
    }
}
