package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long artistID;
    private String name;
    private String description;
    private Long mediaID;
    private Long views;
    private Long likes;
    private Long disLikes;
    private boolean active;
    private boolean flagged;
    private String flaggedReason;
    @Column(updatable = false)
    private Date createdDate;
    private Date updatedDate;
}
