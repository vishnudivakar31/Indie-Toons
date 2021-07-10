package io.wanderingthinkter.indieservice.repositories;

import io.wanderingthinkter.indieservice.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends JpaRepository<Media, Long> {
}
