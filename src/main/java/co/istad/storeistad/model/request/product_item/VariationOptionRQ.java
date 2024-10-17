package co.istad.storeistad.model.request.product_item;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Sattya
 * create at 2/10/2024 7:56 PM
 */
@Data
public class VariationOptionRQ {
    @NotNull(message = "variationOptionId is required")
    private Long variationOptionId;

    @NotNull(message = "status is required")
    private Boolean status;
}
