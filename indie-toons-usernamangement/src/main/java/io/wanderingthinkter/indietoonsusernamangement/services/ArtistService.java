package io.wanderingthinkter.indietoonsusernamangement.services;

import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import io.wanderingthinkter.indietoonsusernamangement.repositories.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepo artistRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    public Artist createArtist(Artist artist) {
        Date date = new Date();
        artist.setPassword(bCryptPasswordEncoder.encode(artist.getPassword()));
        artist.setCreatedDate(date);
        artist.setUpdatedDate(date);
        artist.setActive(true);
        try {
            Artist createdArtist = artistRepo.save(artist);
            sendVerificationEmail(createdArtist);
            return createdArtist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    public Optional<Artist> getArtist(Long id) {
        return artistRepo.findById(id);
    }

    public Optional<Artist> getArtist(String username) {
        return artistRepo.findByUsername(username);
    }

    private void sendVerificationEmail(Artist artist) throws MessagingException {
        UUID uuid = UUID.randomUUID();
        artist.setVerificationCode(uuid.toString());
        String htmlMessage = String.format("<b>Hi %s</b>, " +
                "<br/> <h4>Welcome to Indie Toons</h4> <br/> <br/>" +
                "<i>Please click the following link to get verified.</i> <br/>" +
                "<i>http://localhost:5010/usermanagement/%d/verify?verificationCode=%s</i>",
                artist.getName(), artist.getId(), artist.getVerificationCode());
        artist.setUpdatedDate(new Date());
        emailService.sendEmail(artist.getEmail(), "NOREPLY: Verification email. Welcome to indie toons.", htmlMessage);
        artistRepo.save(artist);
    }
}
