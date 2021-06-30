package io.wanderingthinkter.mediaservice.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "media_records")
@Data
public class MediaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    public MediaRecord() {
    }

    public MediaRecord(Long id, String filePath, String thumbnailPath, Date createdDate, Date updatedDate) {
        this.id = id;
        this.filePath = filePath;
        this.thumbnailPath = thumbnailPath;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
