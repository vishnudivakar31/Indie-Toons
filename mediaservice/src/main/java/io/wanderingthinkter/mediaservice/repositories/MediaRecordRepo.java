package io.wanderingthinkter.mediaservice.repositories;

import io.wanderingthinkter.mediaservice.models.MediaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRecordRepo extends JpaRepository<MediaRecord, Long> {
}
