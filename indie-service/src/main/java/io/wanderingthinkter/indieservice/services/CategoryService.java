package io.wanderingthinkter.indieservice.services;

import io.wanderingthinkter.indieservice.models.Category;
import io.wanderingthinkter.indieservice.models.CategoryType;
import io.wanderingthinkter.indieservice.models.CategoryRequest;
import io.wanderingthinkter.indieservice.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryType> getAllCategoryTypes() {
        return Arrays.asList(CategoryType.values());
    }

    public List<Category> enrollToCategories(CategoryRequest categoryRequest) {
        List<Category> result = categoryRequest.getCategoryTypes()
                .parallelStream()
                .map(categoryType -> {
                    Category category = new Category();
                    category.setArtistID(categoryRequest.getArtistID());
                    category.setCategoryType(categoryType);
                    category.setCreatedDate(new Date());
                    if (categoryType == CategoryType.OTHER) {
                        category.setCategoryName(categoryRequest.getCategoryName());
                    }
                    return category;
                }).collect(Collectors.toList());
        return categoryRepo.saveAll(result);
    }

    public List<Category> getAllCategories(Long artistID) {
        List<Category> allCategories = categoryRepo.findAllByArtistID(artistID);
        return allCategories;
    }

    public List<Long> searchForArtists(CategoryRequest categoryRequest) {
        List<Category> categories = categoryRepo.findAll();
        List<Long> artists = categories
                .parallelStream()
                .filter(category -> categoryRequest.getCategoryTypes().contains(category.getCategoryType()) ||
                    (category.getCategoryName() != null && category.getCategoryName().equals(categoryRequest.getCategoryName()))
                )
                .map(item -> item.getArtistID())
                .collect(Collectors.toList());
        return artists;
    }

    public List<Category> leaveCategories(List<Category> categories) {
        AtomicReference<Long> artistId = new AtomicReference<>();
        categories.stream().findFirst().ifPresent(item -> artistId.set(item.getArtistID()));
        categoryRepo.deleteAll(categories);
        if (artistId != null) {
            return categoryRepo.findAllByArtistID(artistId.get());
        }
        return Collections.emptyList();
    }
}
