package io.wanderingthinkter.indietoonsusernamangement.services;

import io.wanderingthinkter.indietoonsusernamangement.models.Artist;
import io.wanderingthinkter.indietoonsusernamangement.repositories.ArtistRepo;
import io.wanderingthinkter.indietoonsusernamangement.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ArtistService {

    private final String PROFILE_DIRECTORY = "images/profile_picture";

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
                "<i>http://localhost:5010/usermanagement/artist/%d/verify?verificationCode=%s</i>",
                artist.getName(), artist.getId(), artist.getVerificationCode());
        artist.setUpdatedDate(new Date());
        artist.setVerified(false);
        emailService.sendEmail(artist.getEmail(), "NOREPLY: Verification email. Welcome to indie toons.", htmlMessage);
        artistRepo.save(artist);
    }

    public ResponseEntity verify(Long id, String verificationCode) {
        Optional<Artist> optionalArtist = artistRepo.findById(id);
        if(optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            if (artist.getVerificationCode().equals(verificationCode)) {
                artist.setVerified(true);
                artist.setUpdatedDate(new Date());
                artistRepo.save(artist);
                return new ResponseEntity(Map.of("message", "Welcome to Indie Toons."), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "verification code is not matching. request for another verification email");
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }

    public Artist sendEmailVerification(Long id) throws MessagingException {
        Optional<Artist> optionalArtist = artistRepo.findById(id);
        if (optionalArtist.isPresent()) {
            sendVerificationEmail(optionalArtist.get());
            return optionalArtist.get();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }

    public Artist uploadProfilePicture(Long id, MultipartFile multipartFile) throws IOException {
        Optional<Artist> optionalArtist = artistRepo.findById(id);
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            String fileName = artist.getId() + ".png";
            String storedPath = FileUploadUtil.saveFile(PROFILE_DIRECTORY, fileName, multipartFile);
            artist.setProfilePicturePath(storedPath);
            artist.setUpdatedDate(new Date());
            return artistRepo.save(artist);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }

    public byte[] getProfilePicture(Long id) {
        Optional<Artist> optionalArtist = artistRepo.findById(id);
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            if (artist.getProfilePicturePath().length() == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "no profile picture found.");
            }
            try {
                Path path = Paths.get(artist.getProfilePicturePath());
                byte[] data = Files.readAllBytes(path);
                return data;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }

    public boolean login(String username, String password) {
        Optional<Artist> optionalArtist = artistRepo.findByUsername(username);
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            if (bCryptPasswordEncoder.matches(password, artist.getPassword())) {
                if (artist.isActive()) return true;
                else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "account not active");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username and password not matching");
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please check username");
    }

    public Artist disableAccount(Long id) {
        Optional<Artist> optionalArtist = artistRepo.findById(id);
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            artist.setActive(false);
            artist.setUpdatedDate(new Date());
            return artistRepo.save(artist);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }

    public Artist enable(String username, String password) {
        Optional<Artist> optionalArtist = artistRepo.findByUsername(username);
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            if (bCryptPasswordEncoder.matches(password, artist.getPassword())) {
                artist.setActive(true);
                artist.setUpdatedDate(new Date());
                return artistRepo.save(artist);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username and password not matching");
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "artist is not present. please create an account");
    }
}
