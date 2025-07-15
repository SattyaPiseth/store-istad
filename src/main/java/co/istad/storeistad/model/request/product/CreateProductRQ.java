package co.istad.storeistad.model.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Sattya
 * create at 2/9/2024 5:53 PM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRQ {
    @NotBlank(message = "Name is required!")
    @Size(min = 5,max = 255)
    private String name;

    @NotBlank(message = "Description is required!")
    @Size(min = 5,message = "Description must be greater than 5")
    private String description;

    @NotBlank
    private String image;

    @NotNull(message = "Category ID is required!")
    @Positive(message = "Category ID must be greater than 0")
    private Integer categoryId;
}
