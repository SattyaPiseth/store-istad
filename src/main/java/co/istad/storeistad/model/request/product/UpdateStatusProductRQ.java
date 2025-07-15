package co.istad.storeistad.model.request.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Sattya
 * create at 2/9/2024 6:02 PM
 */
@Data
public class UpdateStatusProductRQ {
//    @NotBlank(message = "uuid is required")
//    private String uuid;

    @NotNull(message = "status is required")
    private Boolean status;
}
