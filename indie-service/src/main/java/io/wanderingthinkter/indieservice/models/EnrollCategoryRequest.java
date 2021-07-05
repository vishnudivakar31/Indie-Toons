package io.wanderingthinkter.indieservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnrollCategoryRequest {
    private List<CategoryType> categoryTypes;
    private String categoryName;
    private Long artistID;
}
