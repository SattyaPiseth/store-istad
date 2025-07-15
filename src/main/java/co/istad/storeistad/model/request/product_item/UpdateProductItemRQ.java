package co.istad.storeistad.model.request.product_item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Sattya
 * create at 2/9/2024 10:05 PM
 */
@Data
public class UpdateProductItemRQ {
    @Positive(message = "Product id must be greater than 0")
    @NotNull(message = "Product id is required")
    private Integer productId;

    private Integer quantity;

    private String image;

//    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

//    @NotEmpty(message = "variationOptions is required")
    Set<@Valid VariationOptionRQ> variationOptions;
}
