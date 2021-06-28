package io.wanderingthinkter.indietoonsusernamangement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import io.wanderingthinkter.indietoonsusernamangement.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;
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

    @GetMapping("/{id}/verify")
    public ResponseEntity verifyArtist(@PathVariable Long id, @RequestParam String verificationCode) {
        return artistService.verify(id, verificationCode);
    }

    @GetMapping("/{id}/send_email_verification")
    public Artist sendEmailVerification(@PathVariable Long id) throws MessagingException {
        return artistService.sendEmailVerification(id);
    }

    @GetMapping()
    public Artist getArtist(@RequestParam String username) {
        Optional<Artist> optionalArtist = artistService.getArtist(username);
        if (optionalArtist.isPresent()) {
            return optionalArtist.get();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not found. please sign in first");
    }

    @PostMapping("/{id}/upload_profile_picture")
    public Artist uploadProfilePicture(@PathVariable Long id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        return artistService.uploadProfilePicture(id, multipartFile);
    }

    @GetMapping(value = "/{id}/profile_picture", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) {
        byte[] imageData = artistService.getProfilePicture(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Map<String, String> payload) {
        if (payload.containsKey("username") && payload.containsKey("password")) {
            return artistService.login(payload.get("username"), payload.get("password"));
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "payload should have username and password");
    }

    @PostMapping("/enable")
    public Artist enable(@RequestBody Map<String, String> payload) {
        if (payload.containsKey("username") && payload.containsKey("password")) {
            return artistService.enable(payload.get("username"), payload.get("password"));
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "payload should have username and password");
    }

    @GetMapping("/{id}/disable")
    public Artist disableAccount(@PathVariable Long id) {
        return artistService.disableAccount(id);
    }

    @GetMapping("/reset_password_email")
    public boolean resetPasswordEmail(@RequestParam String username) throws MessagingException {
        return artistService.sendResetPasswordEmail(username);
    }

    @GetMapping("/reset_password")
    public Artist resetPassword(@RequestParam String username, @RequestParam String password) {
        return artistService.resetPassword(username, password);
    }

}
