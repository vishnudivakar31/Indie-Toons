package io.wanderingthinkter.indietoonsusernamangement.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "artists")
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private Date dob;
    private String name;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    private boolean active;
    private String profilePicturePath;
    private boolean verified;
    private String verificationCode;

    public Artist() {
    }

    public Artist(Long id, String username, String email, String password, Date dob, String name, Date createdDate,
                  Date updatedDate, boolean active, String profilePicturePath, boolean verified, String verificationCode) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.active = active;
        this.profilePicturePath = profilePicturePath;
        this.verified = verified;
        this.verificationCode = verificationCode;
    }
}
