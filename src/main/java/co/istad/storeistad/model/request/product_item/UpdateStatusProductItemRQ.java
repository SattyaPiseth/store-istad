package co.istad.storeistad.model.request.product_item;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Sattya
 * create at 2/9/2024 10:19 PM
 */
@Data
public class UpdateStatusProductItemRQ {
//    @NotNull(message = "productItemId is required")
//    private Long productItemId;

    @NotNull(message = "status is required")
    private Boolean status;
}
