package io.wanderingthinkter.indieservice.services;

import io.wanderingthinkter.indieservice.models.Category;
import io.wanderingthinkter.indieservice.models.CategoryType;
import io.wanderingthinkter.indieservice.models.EnrollCategoryRequest;
import io.wanderingthinkter.indieservice.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryType> getAllCategoryTypes() {
        return Arrays.asList(CategoryType.values());
    }

    public List<Category> enrollToCategories(EnrollCategoryRequest enrollCategoryRequest) {
        List<Category> result = enrollCategoryRequest.getCategoryTypes()
                .parallelStream()
                .map(categoryType -> {
                    Category category = new Category();
                    category.setArtistID(enrollCategoryRequest.getArtistID());
                    category.setCategoryType(categoryType);
                    category.setCreatedDate(new Date());
                    if (categoryType == CategoryType.OTHER) {
                        category.setCategoryName(enrollCategoryRequest.getCategoryName());
                    }
                    return category;
                }).collect(Collectors.toList());
        return categoryRepo.saveAll(result);
    }
}
