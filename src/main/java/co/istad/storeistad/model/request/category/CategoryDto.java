package co.istad.storeistad.model.request.category;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link co.istad.storeistad.model.request.category.CategoryDto CategoryDto}
 */
public record CategoryDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        Long parentId){

}