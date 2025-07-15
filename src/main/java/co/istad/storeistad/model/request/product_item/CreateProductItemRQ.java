package co.istad.storeistad.model.request.product_item;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Sattya
 * create at 2/9/2024 9:39 PM
 */
@Data
public class CreateProductItemRQ {

    @NotNull(message = "Product id must be not null")
    @Positive(message = "Product id must be greater than 0")
    private Integer productId;

    @NotNull(message = "Quantity must be not null")
    private Integer quantity;

    @NotBlank(message = "image is required")
    private String image;

    @NotNull(message = "Price must be not null")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @NotEmpty(message = "variationOptions is required")
    Set<@Valid VariationOptionRQ> variationOptions;
}
