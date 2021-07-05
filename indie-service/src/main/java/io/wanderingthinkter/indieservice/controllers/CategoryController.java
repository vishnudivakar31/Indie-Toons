package io.wanderingthinkter.indieservice.controllers;

import io.wanderingthinkter.indieservice.models.Category;
import io.wanderingthinkter.indieservice.models.CategoryType;
import io.wanderingthinkter.indieservice.models.CategoryRequest;
import io.wanderingthinkter.indieservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(@RequestParam Long artistID) {
        return categoryService.getAllCategories(artistID);
    }

    @PostMapping("/search")
    public List<Long> getArtistsWithCategories(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.searchForArtists(categoryRequest);
    }

    @GetMapping("/all")
    public List<CategoryType> getAllCategoryTypes() {
        return categoryService.getAllCategoryTypes();
    }

    @PostMapping("/enroll")
    public List<Category> enrollToCategories(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.enrollToCategories(categoryRequest);
    }

}
