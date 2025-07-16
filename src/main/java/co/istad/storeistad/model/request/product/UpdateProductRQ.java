package co.istad.storeistad.model.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sattya
 * create at 2/9/2024 5:56 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRQ {
    @Size(min = 5,max = 255,message = "Name must be between 5 and 255")
    private String name;
    @Size(min = 5,message = "Description must be greater than 5")
    private String description;
    @Positive(message = "Unit price must be greater than 0")
    private Integer categoryId;
    private String image;
}
