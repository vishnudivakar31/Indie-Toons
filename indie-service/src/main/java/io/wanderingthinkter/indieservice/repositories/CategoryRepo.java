package io.wanderingthinkter.indieservice.repositories;

import io.wanderingthinkter.indieservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
